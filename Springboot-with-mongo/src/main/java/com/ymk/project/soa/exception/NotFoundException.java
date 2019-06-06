package com.ymk.project.soa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String errorCode;
    private final String errorMessageCode;

    public NotFoundException(String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessageCode = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessageCode() {
        return errorMessageCode;
    }
}
