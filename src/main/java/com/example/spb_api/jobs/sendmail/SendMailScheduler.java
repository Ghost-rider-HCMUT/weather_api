package com.example.spb_api.jobs.sendmail;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SendMailScheduler {
    private final SendMailService mailService;

    @Scheduled(fixedRateString = "${app.time-schedule}")
//    @Scheduled(cron = "${spring.mail.send.cron}")
    public void sendMail() {
        Mail mail = Mail.builder()
                .mailTo("phonghq2103@gmail.com")
                .mailSubject("WEATHER REPORT BY WEATHER_API")
                .mailContent(mailService.getWeatherAverageByCity())
                .build();
        mailService.sendMail(mail);
    }
}
