package com.credibanco.assessment.card.service.impl;

import com.credibanco.assessment.card.dto.AnularTransaccionDto;
import com.credibanco.assessment.card.dto.TransaccionDto;
import com.credibanco.assessment.card.exceptions.BankException;
import com.credibanco.assessment.card.model.TarjetaModel;
import com.credibanco.assessment.card.model.TransaccionModel;
import com.credibanco.assessment.card.model.repository.TarjetaRepository;
import com.credibanco.assessment.card.model.repository.TransaccionRepository;
import com.credibanco.assessment.card.service.TransaccionService;
import com.credibanco.assessment.card.utils.Utility;
import com.credibanco.assessment.card.utils.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {
    private final TransaccionRepository repository;
    private final TarjetaRepository tarjetaRepository;
    @Override
    public TransaccionDto crearTransaccion(TransaccionDto dto) throws BankException {
        TarjetaModel tarjeta = this.tarjetaRepository.findById(Long.parseLong(dto.getPan())).orElseThrow(()-> new BankException("01", Constants.TARJETA_NO_ENCONTRADA, HttpStatus.NOT_FOUND));
        if(!tarjeta.getEstado().equals(Constants.ESTADO_ENROLADO)){
            throw new BankException("02", Constants.TARJETA_NO_ENROLADA, HttpStatus.CONFLICT);
        }
        dto.setEstado("Aprovada");
        TransaccionModel transaccion = this.repository.save(toModel(dto));
        TransaccionDto response = toDto(transaccion);
        response.setPan(Utility.enmascararPan(Long.parseLong(transaccion.getPan())));
        return response;
    }

    @Override
    public boolean anularTransaccion(AnularTransaccionDto dto) throws BankException {
        TransaccionModel transaccion = this.repository.findById(dto.getReferencia()).orElseThrow(()-> new BankException("01", Constants.REFERENCIA_NO_ENCONTRADA, HttpStatus.NOT_FOUND));
        TarjetaModel tarjeta = this.tarjetaRepository.findById(Long.parseLong(dto.getPan())).orElseThrow(()-> new BankException("02", Constants.MENSAJE_ERROR_COMPRA_ANULADA, HttpStatus.NOT_FOUND));
        if(transaccion.getFechaCreacion().plusMinutes(5).isAfter(LocalDateTime.now())
                && tarjeta.getPan().toString().equals(dto.getPan())
                && Objects.equals(transaccion.getTotalCompra(), dto.getTotalCompra())){
            transaccion.setEstado("Anulada");
            this.repository.save(transaccion);
            return true;
        }
        return false;
    }

    private TransaccionModel toModel(TransaccionDto dto){
        return new TransaccionModel(dto.getReferencia(), dto.getPan(), dto.getTotalCompra(), dto.getDireccion(), dto.getEstado(), dto.getFechaCreacion());
    }

    private TransaccionDto toDto(TransaccionModel dto){
        return new TransaccionDto(dto.getReferencia(), dto.getPan(), dto.getTotalCompra(), dto.getDireccion(), dto.getEstado(), dto.getFechaCreacion());
    }
}
