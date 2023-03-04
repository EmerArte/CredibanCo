package com.credibanco.assessment.card.service;

import com.credibanco.assessment.card.dto.TarjetaDto;
import com.credibanco.assessment.card.model.TarjetaModel;

import java.util.Collection;

public interface TarjetaService {

    TarjetaDto crearTarjeta(TarjetaDto tarjeta);

    TarjetaDto activarTarjeta(int enroll);

    Collection<TarjetaDto> listarTarjetas();

}
