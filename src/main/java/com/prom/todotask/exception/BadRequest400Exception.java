package com.prom.todotask.exception;


import org.springframework.http.HttpStatus;

public class BadRequest400Exception extends AbstractException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public BadRequest400Exception(String message) {
        super(HTTP_STATUS, message);
    }
}
