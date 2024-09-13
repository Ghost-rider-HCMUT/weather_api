package com.example.spb_api.service;

import com.example.spb_api.dto.weather.WeatherDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WeatherService {

    /**
     * Retrieve all weather data for cities with pagination.
     *
     * @param pageNo   The page number (zero-based)
     * @param pageSize The size of each page
     * @return A Page of WeatherDTO objects
     */
    Page<WeatherDTO> getAllCityWeather(Integer pageNo, Integer pageSize);

    /**
     * Get the latest weather data for a specific city.
     *
     * @param city The name of the city
     * @return WeatherDTO object containing the weather data
     */
    WeatherDTO getWeatherByCityName(String city);

    /**
     * Create a new weather record.
     *
     * @param weatherDTO The WeatherDTO object containing the weather data to be created
     * @return The created WeatherDTO object
     */
    WeatherDTO createWeather(WeatherDTO weatherDTO);

    /**
     * Update the weather data for a specific city.
     *
     * @param city       The name of the city
     * @param weatherDTO The WeatherDTO object containing the updated weather data
     * @return The updated WeatherDTO object
     */
    WeatherDTO updateWeatherByCityName(String city, WeatherDTO weatherDTO);

    /**
     * Delete all weather data for a specific city.
     *
     * @param city The name of the city
     */
    void deleteWeatherByCityName(String city);

    /**
     * Get all weather records for a specific city.
     *
     * @param city The name of the city
     * @return A List of WeatherDTO objects
     */
    List<WeatherDTO> getWeathersByCityName(String city);
}