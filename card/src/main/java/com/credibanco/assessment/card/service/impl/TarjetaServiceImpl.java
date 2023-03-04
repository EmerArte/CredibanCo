package com.credibanco.assessment.card.service.impl;

import com.credibanco.assessment.card.dto.TarjetaDto;
import com.credibanco.assessment.card.model.TarjetaModel;
import com.credibanco.assessment.card.model.repository.TarjetaRepository;
import com.credibanco.assessment.card.service.TarjetaService;
import com.credibanco.assessment.card.utils.enums.TipoTarjetaEnum;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Random;

@Service
public class TarjetaServiceImpl implements TarjetaService {
    private final TarjetaRepository repository;

    public TarjetaServiceImpl(TarjetaRepository repository) {
        this.repository = repository;
    }

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
    public TarjetaDto activarTarjeta(int enroll) {
        return null;
    }

    @Override
    public Collection<TarjetaDto> listarTarjetas() {
        return null;
    }


    private String enmascararPan(@NotNull Long pan){
        String unmask = String.valueOf(pan);
        char[] caracteres = unmask.toCharArray();
        for (int i=5; i<=caracteres.length - 4; i++ ){
            caracteres[i] = '*';
        }
        return new String(caracteres);
    }

    private int generarEnroll(){
        Random rnd = new Random();
        return rnd.nextInt(1, 100);
    }

    private TarjetaModel toModel(TarjetaDto dto){
        return new TarjetaModel(
                Long.parseLong(dto.getPan()),
                dto.getEnroll(),
                dto.getTitular(),
                Long.parseLong(dto.getCedula()),
                dto.getTipo().getValue(),
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
                TipoTarjetaEnum.valueOf(model.getTipo()),
                model.getTelefono(),
                model.getEstado()
        );
    }
}
/*
private Long pan;
    @Column(length = 3, nullable = false, updatable = false)
    private int enroll;
    @Column(length = 40, nullable = false)
    private String titular;
    @Column(length = 15, nullable = false)
    private Long cedula;
    @Column(nullable = false)
    private String tipo;
    @Column(length = 10, nullable = false)
    private String telefono;
    @Column(columnDefinition = "default 'Creada'", nullable = false)
    private String estado;

 */