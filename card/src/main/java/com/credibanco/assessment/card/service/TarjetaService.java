package com.credibanco.assessment.card.service;

import com.credibanco.assessment.card.dto.TarjetaDto;
import com.credibanco.assessment.card.exceptions.BankException;

import java.util.Collection;

public interface TarjetaService {

    TarjetaDto crearTarjeta(TarjetaDto tarjeta);

    TarjetaDto activarTarjeta(String pan, int enroll) throws BankException;

    TarjetaDto getTarjeta(String pan) throws BankException;

    boolean eliminarTarjeta(String pan, int enroll)throws BankException;

    Collection<TarjetaDto> listarTarjetas();

}
