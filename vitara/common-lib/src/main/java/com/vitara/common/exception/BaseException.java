package com.vitara.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class BaseException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
