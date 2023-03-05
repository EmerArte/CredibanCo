package com.credibanco.assessment.card.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Table(name = "TRANSACCION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionModel implements Serializable {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "user_sequence"),
                    @Parameter(name = "initial_value", value = "100000"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long referencia;
    @Column(length = 19, nullable = false)
    private String pan;
    @Column(precision = 2, nullable = false)
    private Double totalCompra;
    @Column(length = 100)
    private String direccion;
    @Column(length = 10, nullable = false)
    private String estado;
    @CreationTimestamp
    private LocalDateTime fechaCreacion;



}
