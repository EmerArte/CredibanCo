package com.credibanco.assessment.card.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "TARJETAS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarjetaModel implements Serializable {
    @Id
    @Column(length = 19)
    private Long pan;
    @Column(length = 3, nullable = false, updatable = false)
    private int enroll;
    @Column(length = 40, nullable = false)
    private String titular;
    @Column(length = 15, nullable = false)
    private Long cedula;
    @Column(length = 15,nullable = false)
    private String tipo;
    @Column(length = 10, nullable = false)
    private String telefono;
    @Column(columnDefinition = "varchar(10) default 'Creada'", nullable = false)
    private String estado;


}
