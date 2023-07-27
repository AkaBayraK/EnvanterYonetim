package com.exceptions;

import java.util.Date;

import lombok.Data;

@Data
public class ApiErrorResponse {
    private final String guid;
    private final String errorCode;
    private final String message;
    private final Integer statusCode;
    private final String statusName;
    private final String path;
    private final String method;
    private final Date timestamp;
}