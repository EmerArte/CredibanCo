package com.credibanco.assessment.card.service.impl;

import com.credibanco.assessment.card.dto.TarjetaDto;
import com.credibanco.assessment.card.exceptions.BankException;
import com.credibanco.assessment.card.model.TarjetaModel;
import com.credibanco.assessment.card.model.repository.TarjetaRepository;
import com.credibanco.assessment.card.service.TarjetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
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
            response.setPan(enmascararPan(save.getPan()));
            return response;
    }

    @Override
    public TarjetaDto activarTarjeta(String pan, int enroll) throws BankException {
            Long panNumber = Long.parseLong(pan);
            TarjetaModel tarjeta = this.repository.findById(panNumber).orElseThrow(()-> new BankException("01", "Tarjeta no existe"));
            if(tarjeta.getEnroll() == enroll){
                tarjeta.setEstado("Enrolada");
                TarjetaModel update = this.repository.save(tarjeta);
                TarjetaDto response = toDto(update);
                response.setPan(enmascararPan(update.getPan()));
                return response;
            }else {
                throw new BankException("02","Número de validación inválido");
            }


    }

    @Override
    public TarjetaDto getTarjeta(String pan) throws BankException {
        if(pan.matches("^[0-9]+$")){
            TarjetaModel tarjeta = this.repository.findById(Long.valueOf(pan)).orElseThrow(()-> new BankException("01", "Tarjeta no existe"));
            TarjetaDto response = toDto(tarjeta);
            response.setPan(enmascararPan(tarjeta.getPan()));
            return response;
        }else {
            throw new BankException("02","Número de validación inválido");
        }
    }

    @Override
    public boolean eliminarTarjeta(String pan, int enroll) throws BankException {
        Long panNumber = Long.parseLong(pan);
        TarjetaModel tarjeta = this.repository.findById(panNumber).orElseThrow(()-> new BankException("02", "Tarjeta no existe"));
        if(tarjeta.getEnroll() == enroll){
            try{
                this.repository.delete(tarjeta);
                return true;
            }catch (Exception e){
                return false;
            }
        }else {
            throw new BankException("01","No se ha eliminado la tarjeta");
        }
    }

    @Override
    public Collection<TarjetaDto> listarTarjetas() {
        List<TarjetaDto> responseList = new ArrayList<>();
        Collection<TarjetaModel> dataBaseCollection = this.repository.findAll();
        dataBaseCollection.forEach(tarjetaModel ->{
                TarjetaDto dto = toDto(tarjetaModel);
                dto.setPan(enmascararPan(tarjetaModel.getPan()));
                responseList.add(dto);
                }
        );
        return responseList;
    }


    private String enmascararPan(@NotNull Long pan){
        String unmask = String.valueOf(pan);
        char[] caracteres = unmask.toCharArray();
        for (int i=6; i<=caracteres.length - 5; i++ ){
            caracteres[i] = '*';
        }
        return new String(caracteres);
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