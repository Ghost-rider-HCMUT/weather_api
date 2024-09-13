package com.example.spb_api.message;

import lombok.Value;

@Value
public class ErrorMessage {
    int statusCode;
    String message;
}