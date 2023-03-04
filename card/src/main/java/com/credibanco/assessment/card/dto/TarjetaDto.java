package com.credibanco.assessment.card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
public class TarjetaDto {
    @NotNull
    @Size(min = 16, max = 19)
    @Pattern(regexp = "^[0-9]+$", message = "Número de la tarjeta no válido")
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
    private String tipo;
    @NotNull
    @Size(min = 10, max = 10)
    private String telefono;
    private String estado;

}
