package com.prom.todotask.cotrolller.handler.dto;

import lombok.Data;

@Data
public class ExceptionDTO {
    private String message;

    private String url;

    private String error;
}
