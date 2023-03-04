package com.credibanco.assessment.card.utils.enums;

public enum TipoTarjetaEnum {
    CREDITO("Crédito"),
    DEBITO("Débito");
    private final String value;
    TipoTarjetaEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
