package com.example.spb_api.mapper;

import com.example.spb_api.dto.weather.WeatherDTO;
import com.example.spb_api.entity.WeatherEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    WeatherDTO toDto(WeatherEntity entity);

    WeatherEntity toEntity(WeatherDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(WeatherDTO dto, @MappingTarget WeatherEntity entity);
}