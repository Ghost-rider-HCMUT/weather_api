package com.example.spb_api.service.impl;

import com.example.spb_api.dto.weather.WeatherDTO;
import com.example.spb_api.entity.WeatherEntity;
import com.example.spb_api.mapper.WeatherMapper;
import com.example.spb_api.repository.WeatherRepository;
import com.example.spb_api.service.WeatherService;
import com.example.spb_api.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WeatherServiceImpl implements WeatherService {
    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    @Override
    public Page<WeatherDTO> getAllCityWeather(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return weatherRepository.findAllLatest(pageable)
                .map(weatherMapper::toDto);
    }

    @Override
    public WeatherDTO getWeatherByCityName(String city) {
        WeatherEntity weatherEntity = weatherRepository.findLatestByCity(StringUtil.cleanString(city));
        if (weatherEntity == null) {
            throw new RuntimeException("Weather data not found for city: " + city);
        }
        return weatherMapper.toDto(weatherEntity);
    }

    @Override
    @Transactional
    public WeatherDTO createWeather(WeatherDTO weatherDTO) {
        WeatherEntity weatherEntity = weatherMapper.toEntity(weatherDTO);
        WeatherEntity savedEntity = weatherRepository.save(weatherEntity);
        return weatherMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    public WeatherDTO updateWeatherByCityName(String city, WeatherDTO weatherDTO) {
        WeatherEntity weatherEntity = weatherRepository.findLatestByCity(city);
        if (weatherEntity == null) {
            throw new RuntimeException("Weather data not found for city: " + city);
        }
        weatherMapper.updateEntityFromDto(weatherDTO, weatherEntity);
        WeatherEntity updatedEntity = weatherRepository.save(weatherEntity);
        return weatherMapper.toDto(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteWeatherByCityName(String city) {
        weatherRepository.deleteByCity(city);
    }

    @Override
    public List<WeatherDTO> getWeathersByCityName(String city) {
        return weatherRepository.findByCity(StringUtil.cleanString(city)).stream()
                .map(weatherMapper::toDto)
                .collect(Collectors.toList());
    }
}