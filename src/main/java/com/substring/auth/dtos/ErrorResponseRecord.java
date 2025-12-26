package com.substring.auth.dtos;

import org.springframework.http.HttpStatus;

public record ErrorResponseRecord(String message,
                                  HttpStatus status,
                                  int statusCode) {
}
