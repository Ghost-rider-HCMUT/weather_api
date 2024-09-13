package com.example.spb_api.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherDTO {
    @JsonProperty("city")
    private String city;

    @JsonProperty("condition_weather")
    private String conditionWeather;

    @JsonProperty("temp")
    private float temp;

    @JsonProperty("wind_kph")
    private float windKph;

    @JsonProperty("feelslike_c")
    private float feelsLike;

    @JsonProperty("pressure_in")
    private float pressureIn;

    @JsonProperty("humidity")
    private float humidity;

    @JsonProperty("heatindex_c")
    private float heatIndex;

    @JsonProperty("dewpoint_c")
    private float dewPoint;

    @JsonProperty("precip_mm")
    private float precip;

    @JsonProperty("gust_kph")
    private float gustKph;

    @JsonProperty("is_day")
    private float isDay;

    @JsonProperty("time")
    private LocalDateTime time;

}

