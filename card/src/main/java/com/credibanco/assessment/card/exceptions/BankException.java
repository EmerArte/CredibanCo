package com.credibanco.assessment.card.exceptions;

import com.credibanco.assessment.card.dto.ResponseModel;
import org.springframework.http.HttpStatus;

public class BankException extends Exception{
    private String codigo;
    private String message;
    private HttpStatus status;

    public BankException(Throwable cause, String codigo, String message, HttpStatus status) {
        super(cause);
        this.codigo = codigo;
        this.message = message;
        this.status = status;
    }

    public BankException(String codigo, String message, HttpStatus status) {
        this.codigo = codigo;
        this.message = message;
        this.status = status;
    }


    public ResponseModel<Object> getResponse() {
        return new ResponseModel<>(this.codigo, this.message, null);
    }

    public String getCodigo() {
        return codigo;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
