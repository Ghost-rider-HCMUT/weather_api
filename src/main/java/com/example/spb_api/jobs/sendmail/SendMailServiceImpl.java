package com.example.spb_api.jobs.sendmail;

import com.example.spb_api.repository.WeatherRepository;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SendMailServiceImpl implements SendMailService {
    private final JavaMailSender javaMailSender;
    private static final Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);
    private final WeatherRepository weatherRepository;

    @Override
    public void sendMail(Mail mail) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setSubject(mail.getMailSubject());
            helper.setFrom(new InternetAddress("${spring.mail.username}"));
            helper.setTo(mail.getMailTo());

            // Convert mailContent to HTML table
            String htmlContent = formatWeatherDataToHtmlTable(mail.getMailContent());
            helper.setText(htmlContent, true);

            if (mail.getMailCc() != null) {
                helper.setCc(mail.getMailCc());
            }

            if (mail.getMailBcc() != null) {
                helper.setBcc(mail.getMailBcc());
            }

            javaMailSender.send(mimeMessage);
            logger.info("Mail sent successfully to {}", mail.getMailTo());
        } catch (Exception e) {
            logger.error("An error occurred while sending mail", e);
        }
    }

    private String formatWeatherDataToHtmlTable(List<SendMailDTO> weatherData) {
        StringBuilder htmlBuilder = new StringBuilder();
        // Get Day
        Date dateTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateTime = sdf.format(dateTime);

        htmlBuilder.append("<html><body>");
        htmlBuilder.append("<h2>Weather Report: ").append(formattedDateTime).append("</h2>");
        htmlBuilder.append("<table border='1' style='border-collapse: collapse;'>");
        htmlBuilder.append("<tr style='background-color: #f2f2f2;'>");
        htmlBuilder.append("<th>City</th>");
        htmlBuilder.append("<th>Avg Temp (°C)</th>");
        htmlBuilder.append("<th>Avg Wind (km/h)</th>");
        htmlBuilder.append("<th>Avg Feels Like (°C)</th>");
        htmlBuilder.append("<th>Avg Pressure (inHg)</th>");
        htmlBuilder.append("<th>Avg Humidity (%)</th>");
        htmlBuilder.append("<th>Avg Heat Index (°C)</th>");
        htmlBuilder.append("<th>Avg Dew Point (°C)</th>");
        htmlBuilder.append("<th>Avg Precip (mm)</th>");
        htmlBuilder.append("<th>Avg Gust (km/h)</th>");
        htmlBuilder.append("</tr>");

        for (SendMailDTO data : weatherData) {
            htmlBuilder.append("<tr>");
            htmlBuilder.append("<td>").append(data.getCity()).append("</td>");
            htmlBuilder.append("<td>").append(data.getAvgTemp()).append("</td>");
            htmlBuilder.append("<td>").append(data.getAvgWindKph()).append("</td>");
            htmlBuilder.append("<td>").append(data.getAvgFeelsLike()).append("</td>");
            htmlBuilder.append("<td>").append(data.getAvgPressureIn()).append("</td>");
            htmlBuilder.append("<td>").append(data.getAvgHumidity()).append("</td>");
            htmlBuilder.append("<td>").append(data.getAvgHeatIndex()).append("</td>");
            htmlBuilder.append("<td>").append(data.getAvgDewPoint()).append("</td>");
            htmlBuilder.append("<td>").append(data.getAvgPrecip()).append("</td>");
            htmlBuilder.append("<td>").append(data.getAvgGustKph()).append("</td>");
            htmlBuilder.append("</tr>");
        }

        htmlBuilder.append("</table>");
        htmlBuilder.append("</body></html>");

        return htmlBuilder.toString();
    }

    // Handle Data
    @Override
    public List<SendMailDTO> getWeatherAverageByCity() {
        List<Object[]> objectsData = weatherRepository.getAverageWeatherByCity();
        return objectsData.stream()
                .map(this::convertToSendMailDTO)
                .collect(Collectors.toList());
    }

    private SendMailDTO convertToSendMailDTO(Object[] data) {
        return SendMailDTO.builder()
                .city((String) data[0])
                .avgTemp(roundToOneDecimal(((Number) data[1]).doubleValue()))
                .avgWindKph(roundToOneDecimal(((Number) data[2]).doubleValue()))
                .avgFeelsLike(roundToOneDecimal(((Number) data[3]).doubleValue()))
                .avgPressureIn(roundToOneDecimal(((Number) data[4]).doubleValue()))
                .avgHumidity(roundToOneDecimal(((Number) data[5]).doubleValue()))
                .avgHeatIndex(roundToOneDecimal(((Number) data[6]).doubleValue()))
                .avgDewPoint(roundToOneDecimal(((Number) data[7]).doubleValue()))
                .avgPrecip(roundToOneDecimal(((Number) data[8]).doubleValue()))
                .avgGustKph(roundToOneDecimal(((Number) data[9]).doubleValue()))
                .build();
    }

    private double roundToOneDecimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}