package com.letitbee.diamondvaluationsystem.exception;

import org.springframework.http.HttpStatus;

public class CredentialsException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public CredentialsException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public CredentialsException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
