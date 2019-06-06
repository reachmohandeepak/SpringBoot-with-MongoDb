package com.ymk.project.soa.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    private final String error;
    private final String message;
    private final String correlationId;

    public ErrorResponse(String error, String message, String correlationId) {
        this.error = error;
        this.message = message;
        this.correlationId = correlationId;
    }

    public String getError() { return error; }

    public String getMessage() { return message; }

    public String getCorrelationId() {
        return correlationId;
    }
}