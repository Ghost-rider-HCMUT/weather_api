package com.example.spb_api.i18n;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class I18nUtil {
    private final MessageSource messageSource;

    @Resource(name = "localHolder")
    LocalHolder localHolder;

    public String getMessage(String code, String... args) {
        return messageSource.getMessage(code, args, localHolder.getCurrentLocale());
    }

}

