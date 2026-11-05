package com.vitara.common.exception;

import com.vitara.common.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // This will automatically catch any microservice specific exception (ex:CustomerAlreadyExistsException in csutomer service)
    // and any other custom microservice exceptions that extend BusinessException
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(BaseException exception,
                                                                                 WebRequest webRequest){
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }
}