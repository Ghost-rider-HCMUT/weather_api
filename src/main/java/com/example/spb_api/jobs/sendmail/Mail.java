package com.example.spb_api.jobs.sendmail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mail {
    private String language;
    private String mailTo;
    private String mailCc;
    private String mailBcc;
    private String mailSubject;
    private List<SendMailDTO> mailContent;
    private String contentType = "json/plain";
}