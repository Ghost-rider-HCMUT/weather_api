package com.example.spb_api.jobs.callapi;

import com.example.spb_api.entity.WeatherEntity;
import com.example.spb_api.repository.WeatherRepository;
import com.example.spb_api.utils.HandleTimeUtil;
import com.example.spb_api.utils.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
@Component
public class FetchDataScheduler {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WeatherRepository weatherRepository;
    private final String[] fileContent;

    @Value("${app.api-url}")
    private String apiUrl;

    @Value("${app.api-key}")
    private String apiKey;

    @Value("${app.expiration-data}")
    private int expirationTime;

    @Scheduled(fixedRateString = "${app.time-schedule}")
    public void fetchDataAndUpdateDB() {
        AtomicReference<String> lastUpdatedTime = new AtomicReference<>();
        long startTime = System.currentTimeMillis();

        try (ExecutorService executorService = Executors.newFixedThreadPool(5)) {
            List<Future<?>> futures = new ArrayList<>();

            for (String province : fileContent) {
                futures.add(executorService.submit(() -> {
                    try {
                        String url = String.format("%s?key=%s&q=%s", apiUrl, apiKey, province);
                        String jsonResponse = Optional.ofNullable(restTemplate.getForObject(url, String.class))
                                .orElseThrow(() -> new RuntimeException("Failed to fetch data for " + province));

                        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                        JsonNode currentNode = jsonNode.get("current");

                        WeatherEntity weatherEntity = buildWeatherEntity(currentNode, StringUtil.cleanString(province));
                        weatherRepository.save(weatherEntity);

                        String currentUpdatedTime = currentNode.get("last_updated").asText();
                        lastUpdatedTime
                                .updateAndGet(prevTime -> prevTime == null || currentUpdatedTime.compareTo(prevTime) > 0
                                        ? currentUpdatedTime
                                        : prevTime);
                        log.info("Updated weather data for {}", province);
                    } catch (Exception e) {
                        log.error("Error processing weather data for {}: {}", province, e.getMessage());
                    }
                }));
            }

            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    log.error("Error waiting for task to complete: {}", e.getMessage());
                }
            }
        }

        if (lastUpdatedTime.get() != null) {
            deleteOldData(lastUpdatedTime.get());
        }

        long endTime = System.currentTimeMillis();
        log.info("FetchDataAndUpdateDB completed in {} ms", (endTime - startTime));

        System.out.println(fileContent.length);
    }

    private WeatherEntity buildWeatherEntity(JsonNode currentNode, String province) {
        return WeatherEntity.builder()
                .city(province)
                .conditionWeather(currentNode.get("condition").get("text").asText())
                .temp(currentNode.get("temp_c").floatValue())
                .windKph(currentNode.get("wind_kph").floatValue())
                .feelsLike(currentNode.get("feelslike_c").floatValue())
                .pressureIn(currentNode.get("pressure_in").floatValue())
                .humidity(currentNode.get("humidity").floatValue())
                .heatIndex(currentNode.get("heatindex_c").floatValue())
                .dewPoint(currentNode.get("dewpoint_c").floatValue())
                .precip(currentNode.get("precip_mm").floatValue())
                .gustKph(currentNode.get("gust_kph").floatValue())
                .isDay(currentNode.get("is_day").asInt())
                .time(LocalDateTime.parse(currentNode.get("last_updated").asText(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }

    private void deleteOldData(String lastUpdatedTime) {
        try {
            LocalDateTime cutoffTime = HandleTimeUtil.handleTime(lastUpdatedTime).minusHours(expirationTime);
            weatherRepository.deleteByTimeBefore(cutoffTime);
            log.info("Deleted weather data older than {}", cutoffTime);
        } catch (Exception e) {
            log.error("Error deleting old weather data: {}", e.getMessage());
        }
    }

}