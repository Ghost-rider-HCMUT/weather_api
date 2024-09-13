package com.example.spb_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
public class ProvinceConfig {
    @Value("classpath:provinces.txt")
    private Resource resourceFile;

    @Bean
    public String[] provinces() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceFile.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
            return content.toString().trim().split("\\s*,\\s*");
        }
    }
}