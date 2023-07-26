package com.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = -5452339114753423872L;
	private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}