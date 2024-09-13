package com.example.spb_api.jobs.sendmail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SendMailDTO {
    private String city;
    private Double avgTemp;
    private Double avgWindKph;
    private Double avgFeelsLike;
    private Double avgPressureIn;
    private Double avgHumidity;
    private Double avgHeatIndex;
    private Double avgDewPoint;
    private Double avgPrecip;
    private Double avgGustKph;
}