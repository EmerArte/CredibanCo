package com.credibanco.assessment.card.controllers;

import com.credibanco.assessment.card.dto.EnrollDto;
import com.credibanco.assessment.card.dto.ResponseModel;
import com.credibanco.assessment.card.dto.TarjetaDto;
import com.credibanco.assessment.card.exceptions.BankException;
import com.credibanco.assessment.card.service.TarjetaService;
import com.credibanco.assessment.card.utils.constants.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class TarjetaController {
    private final TarjetaService service;

    @PostMapping(value = "/tarjeta")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ResponseModel<TarjetaDto>> crearTarjeta(@RequestBody @Valid TarjetaDto tarjeta) throws BankException {
        try{
            TarjetaDto response = this.service.crearTarjeta(tarjeta);
            return new  ResponseEntity<>(new ResponseModel<>("00", Constants.MENSAJE_EXITOSO, response), HttpStatus.CREATED);
        }catch (Exception e){
            throw new BankException("01", Constants.MENSAJE_FALLIDO);
        }
    }
    @PostMapping(value = "/tarjeta/enrolar")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ResponseModel<TarjetaDto>> activarTarjeta(@RequestBody @Valid EnrollDto enrollDto) throws BankException {
            TarjetaDto response = this.service.activarTarjeta(enrollDto.getPan(), enrollDto.getEnroll());
            return ResponseEntity.ok(new ResponseModel<>("00",Constants.MENSAJE_EXITOSO, response));
    }

    @DeleteMapping(value = "/tarjeta")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ResponseModel<TarjetaDto>> eliminarTarjeta(@RequestBody @Valid EnrollDto enrollDto) throws BankException {
        if(this.service.eliminarTarjeta(enrollDto.getPan(), enrollDto.getEnroll())){
            return ResponseEntity.ok(new ResponseModel<>("00","Se ha eliminado la tarjeta", null));
        }else{
            return ResponseEntity.ok(new ResponseModel<>("01","No se ha eliminado la tarjeta", null));
        }

    }
    @GetMapping(value = "/tarjeta/{pan}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ResponseModel<TarjetaDto>> buscarTarjeta(@PathVariable String pan) throws BankException {
        TarjetaDto response = this.service.getTarjeta(pan);
        return ResponseEntity.ok(new ResponseModel<>("00",Constants.MENSAJE_EXITOSO, response));
    }
    @GetMapping(value = "/tarjetas")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ResponseModel<Collection<TarjetaDto>>> listarTarjetas(){
        return ResponseEntity.ok(new ResponseModel<>("00",Constants.MENSAJE_EXITOSO, this.service.listarTarjetas()));
    }
}
