package com.credibanco.assessment.card.exceptions;

import com.credibanco.assessment.card.dto.ResponseModel;

public class BankException extends Exception{
    private String codigo;
    private String message;

    public BankException(Throwable cause, String codigo, String message) {
        super(cause);
        this.codigo = codigo;
        this.message = message;
    }

    public BankException(String codigo, String message) {
        this.codigo = codigo;
        this.message = message;
    }


    public ResponseModel<Object> getResponse() {
        return new ResponseModel<>(this.codigo, this.message, null);
    }

    public String getCodigo() {
        return codigo;
    }
}
