package com.credibanco.assessment.card.exceptions;

import com.credibanco.assessment.card.dto.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {BankException.class})
    protected ResponseEntity<Object> handleAppException(BankException ex, WebRequest request) {
        log.error("{}", ex.getMessage());
        return handleExceptionInternal(ex, ex.getResponse(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders
            headers, HttpStatus status, WebRequest webRequest) {
        log.error("{}", ex.getMessage());
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            log.error("Invalid {} value submitted for {}", fieldError.getRejectedValue(), fieldError.getField());
            errors.append(String.format(Objects.requireNonNull(fieldError.getDefaultMessage()), fieldError.getField())).append(" | ");
        });
        return handleExceptionInternal(ex, new ResponseModel<>(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                errors.toString(), null), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

}
