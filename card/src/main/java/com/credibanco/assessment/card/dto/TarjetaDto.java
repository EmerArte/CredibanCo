package com.credibanco.assessment.card.dto;

import com.credibanco.assessment.card.utils.enums.TipoTarjetaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
@Data
@AllArgsConstructor
public class TarjetaDto {
    @NotNull
    @Size(min = 16, max = 19)
    @Pattern(regexp = "^[0-9]+$")
    private String pan;
    private int enroll;
    @NotNull
    @Size(min = 6, max = 40)
    private String titular;
    @NotNull
    @Size(min = 10, max = 15)
    @Pattern(regexp = "^[0-9]+$")
    private String cedula;
    @NotNull
    private TipoTarjetaEnum tipo;
    @NotNull
    @Size(min = 10, max = 10)
    private String telefono;
    private String estado;

}
