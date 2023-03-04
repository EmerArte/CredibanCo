package com.credibanco.assessment.card.exceptions;

public class BankException extends Exception{
    private int codigo;
    private String message;

    public BankException(Throwable cause, int codigo, String message) {
        super(cause);
        this.codigo = codigo;
        this.message = message;
    }

    public BankException(int codigo, String message) {
        this.codigo = codigo;
        this.message = message;
    }
    public ExceptionModel<Object> getResponse() {
        return new ExceptionModel<>(this.codigo, this.message, null);
    }

    public int getCodigo() {
        return codigo;
    }
}
