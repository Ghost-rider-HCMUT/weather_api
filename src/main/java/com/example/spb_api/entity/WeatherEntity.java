package com.example.spb_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "weather_information_of_provinces")
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty("city")
    private String city;

    @Column(nullable = false)
    @JsonProperty("condition_weather")
    private String conditionWeather;

    @Column(nullable = false)
    @JsonProperty("temp")
    private float temp;

    @Column(nullable = false)
    @JsonProperty("wind_kph")
    private float windKph;

    @Column(nullable = false)
    @JsonProperty("feelslike_c")
    private float feelsLike;

    @Column(nullable = false)
    @JsonProperty("pressure_in")
    private float pressureIn;

    @Column(nullable = false)
    @JsonProperty("humidity")
    private float humidity;

    @Column(nullable = false)
    @JsonProperty("heatindex_c")
    private float heatIndex;

    @Column(nullable = false)
    @JsonProperty("dewpoint_c")
    private float dewPoint;

    @Column(nullable = false)
    @JsonProperty("precip_mm")
    private float precip;

    @Column(nullable = false)
    @JsonProperty("gust_kph")
    private float gustKph;

    @Column(nullable = false)
    @JsonProperty("is_day")
    private float isDay;

    @Column(nullable = false)
    @JsonProperty("time")
    private LocalDateTime time;
}
