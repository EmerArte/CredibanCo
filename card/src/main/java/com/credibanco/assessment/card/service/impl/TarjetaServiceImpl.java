package com.credibanco.assessment.card.service.impl;

import com.credibanco.assessment.card.dto.TarjetaDto;
import com.credibanco.assessment.card.exceptions.BankException;
import com.credibanco.assessment.card.model.TarjetaModel;
import com.credibanco.assessment.card.model.repository.TarjetaRepository;
import com.credibanco.assessment.card.service.TarjetaService;
import com.credibanco.assessment.card.utils.constants.Constants;
import com.credibanco.assessment.card.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class TarjetaServiceImpl implements TarjetaService {
    private final TarjetaRepository repository;
    @Override
    public TarjetaDto crearTarjeta(TarjetaDto tarjeta) {
            tarjeta.setEnroll(generarEnroll());
            tarjeta.setEstado("Creada");
            TarjetaModel save = repository.save(toModel(tarjeta));
            TarjetaDto response = toDto(save);
            response.setPan(Utility.enmascararPan(save.getPan()));
            return response;
    }

    @Override
    public TarjetaDto activarTarjeta(String pan, int enroll) throws BankException {
            Long panNumber = Long.parseLong(pan);
            TarjetaModel tarjeta = this.repository.findById(panNumber).orElseThrow(()-> new BankException("01", Constants.TARJETA_NO_ENCONTRADA, HttpStatus.NOT_FOUND));
            if(tarjeta.getEnroll() == enroll){
                tarjeta.setEstado("Enrolada");
                TarjetaModel update = this.repository.save(tarjeta);
                TarjetaDto response = toDto(update);
                response.setPan(Utility.enmascararPan(update.getPan()));
                return response;
            }else {
                throw new BankException("02",Constants.NUM_VALIDACION_NO_VALIDA, HttpStatus.BAD_REQUEST);
            }


    }

    @Override
    public TarjetaDto getTarjeta(String pan) throws BankException {
        if(pan.matches("^[0-9]+$")){
            TarjetaModel tarjeta = this.repository.findById(Long.valueOf(pan)).orElseThrow(()-> new BankException("01", Constants.TARJETA_NO_ENCONTRADA, HttpStatus.NOT_FOUND));
            TarjetaDto response = toDto(tarjeta);
            response.setPan(Utility.enmascararPan(tarjeta.getPan()));
            return response;
        }else {
            throw new BankException("02",Constants.NUM_VALIDACION_NO_VALIDA, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public boolean eliminarTarjeta(String pan, int enroll) throws BankException {
        Long panNumber = Long.parseLong(pan);
        TarjetaModel tarjeta = this.repository.findById(panNumber).orElseThrow(()-> new BankException("02", Constants.TARJETA_NO_ENCONTRADA, HttpStatus.NOT_FOUND));
        if(tarjeta.getEnroll() == enroll){
            try{
                this.repository.delete(tarjeta);
                return true;
            }catch (Exception e){
                return false;
            }
        }else{
            throw new BankException("01","No se ha eliminado la tarjeta", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Collection<TarjetaDto> listarTarjetas() {
        List<TarjetaDto> responseList = new ArrayList<>();
        Collection<TarjetaModel> dataBaseCollection = this.repository.findAll();
        dataBaseCollection.forEach(tarjetaModel ->{
                TarjetaDto dto = toDto(tarjetaModel);
                dto.setPan(Utility.enmascararPan(tarjetaModel.getPan()));
                responseList.add(dto);
                }
        );
        return responseList;
    }


    private int generarEnroll() {
        return ThreadLocalRandom.current().nextInt(1, 100 + 1);
    }

    private TarjetaModel toModel(TarjetaDto dto){
        return new TarjetaModel(
                Long.parseLong(dto.getPan()),
                dto.getEnroll(),
                dto.getTitular(),
                Long.parseLong(dto.getCedula()),
                dto.getTipo(),
                dto.getTelefono(),
                dto.getEstado()
        );
    }
    private TarjetaDto toDto(TarjetaModel model){
        return new TarjetaDto(
                String.valueOf(model.getPan()),
                model.getEnroll(),
                model.getTitular(),
                String.valueOf(model.getCedula()),
                model.getTipo(),
                model.getTelefono(),
                model.getEstado()
        );
    }
}