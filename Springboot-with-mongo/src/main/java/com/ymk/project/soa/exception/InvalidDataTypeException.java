package com.ymk.project.soa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDataTypeException extends RuntimeException {

    private final String parameterName;

    public InvalidDataTypeException(String parameterName){
        this.parameterName = parameterName;
    }

    public String getParameterName() {
        return parameterName;
    }


}
