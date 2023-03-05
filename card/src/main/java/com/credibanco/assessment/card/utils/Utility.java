package com.credibanco.assessment.card.utils;

import javax.validation.constraints.NotNull;

public abstract class Utility {
    public static String enmascararPan(@NotNull Long pan){
        String unmask = String.valueOf(pan);
        char[] caracteres = unmask.toCharArray();
        for (int i=6; i<=caracteres.length - 5; i++ ){
            caracteres[i] = '*';
        }
        return new String(caracteres);
    }
}
