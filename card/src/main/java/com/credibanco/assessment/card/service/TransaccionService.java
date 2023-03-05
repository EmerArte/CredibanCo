package com.credibanco.assessment.card.service;

import com.credibanco.assessment.card.dto.AnularTransaccionDto;
import com.credibanco.assessment.card.dto.TransaccionDto;
import com.credibanco.assessment.card.exceptions.BankException;

public interface TransaccionService {

    TransaccionDto crearTransaccion(TransaccionDto dto) throws BankException;
    boolean anularTransaccion(AnularTransaccionDto dto) throws BankException;

}
