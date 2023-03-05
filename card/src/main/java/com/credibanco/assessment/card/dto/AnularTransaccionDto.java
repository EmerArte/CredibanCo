package com.credibanco.assessment.card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class AnularTransaccionDto {
    @NotNull
    private Long referencia;
    @NotNull
    @Size(min = 16, max = 19)
    @Pattern(regexp = "^[0-9]+$", message = "Número de la tarjeta no válido")
    private String pan;
    @NotNull
    @DecimalMin("0.1")
    private Double totalCompra;
}
