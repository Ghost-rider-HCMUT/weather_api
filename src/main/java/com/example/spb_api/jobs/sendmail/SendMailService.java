package com.example.spb_api.jobs.sendmail;

import java.util.List;

public interface SendMailService {
    void sendMail(Mail mail);

    List<SendMailDTO> getWeatherAverageByCity();
}
