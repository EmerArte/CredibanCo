package com.credibanco.assessment.card.controllers;

import com.credibanco.assessment.card.dto.*;
import com.credibanco.assessment.card.exceptions.BankException;
import com.credibanco.assessment.card.service.TransaccionService;
import com.credibanco.assessment.card.utils.constants.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransaccionController {
    private final TransaccionService service;

    @PostMapping(value = "/transaccion")
    public ResponseEntity<ResponseModel<TransaccionDto>> crearTransaccion(@RequestBody @Valid TransaccionDto transaccionDto) throws BankException {
        try{
            TransaccionDto response = this.service.crearTransaccion(transaccionDto);
            return new  ResponseEntity<>(new ResponseModel<>("00", Constants.MENSAJE_EXITOSO, response), HttpStatus.CREATED);
        }catch (Exception e){
            throw new BankException("99", Constants.MENSAJE_FALLIDO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "/transaccion/anular")
    public ResponseEntity<ResponseModel<Long>> activarTarjeta(@RequestBody @Valid AnularTransaccionDto anular) throws BankException {
        if(this.service.anularTransaccion(anular)){
            return ResponseEntity.ok(new ResponseModel<>("00",Constants.MENSAJE_EXITOSO_COMPRA_ANULADA, anular.getReferencia()));
        }else {
            return ResponseEntity.ok(new ResponseModel<>("02",Constants.MENSAJE_ERROR_COMPRA_ANULADA, anular.getReferencia()));
        }
    }
}
