package com.example.spb_api.controller;

import com.example.spb_api.dto.weather.WeatherDTO;
import com.example.spb_api.i18n.I18nUtil;
import com.example.spb_api.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/weather")
public class WeatherController {
    private final WeatherService weatherService;
    private final I18nUtil i18nUtil;

    @GetMapping
    public ResponseEntity<Page<WeatherDTO>> getWeather(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<WeatherDTO> weatherDTOList = weatherService.getAllCityWeather(pageNo, pageSize);
        Locale locale = LocaleContextHolder.getLocale();
        String message = i18nUtil.getMessage("success.message", String.valueOf(locale));
        System.out.println(message);
        return ResponseEntity.ok(weatherDTOList);
    }

    @PostMapping
    public ResponseEntity<WeatherDTO> createWeather(@RequestBody WeatherDTO weatherDTO) {
        WeatherDTO createdWeatherDTO = weatherService.createWeather(weatherDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWeatherDTO);
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherDTO> getWeatherByCity(@PathVariable String city) {
        WeatherDTO weatherDTO = weatherService.getWeatherByCityName(city);
        return ResponseEntity.ok(weatherDTO);
    }

    @PutMapping("/{city}")
    public ResponseEntity<WeatherDTO> updateWeather(@PathVariable String city, @RequestBody WeatherDTO weatherDTO) {
        WeatherDTO updatedWeatherDTO = weatherService.updateWeatherByCityName(city, weatherDTO);
        return ResponseEntity.ok(updatedWeatherDTO);
    }

    @DeleteMapping("/{city}")
    public ResponseEntity<String> deleteWeather(@PathVariable String city) {
        weatherService.deleteWeatherByCityName(city);
        return ResponseEntity.ok("Weather data for city " + city + " deleted successfully");
    }

    @GetMapping("/search/{city}")
    public ResponseEntity<List<WeatherDTO>> searchWeather(@PathVariable String city) {
        List<WeatherDTO> weatherDTOList = weatherService.getWeathersByCityName(city);
        return ResponseEntity.ok(weatherDTOList);
    }
}