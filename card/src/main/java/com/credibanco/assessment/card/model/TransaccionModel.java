package com.credibanco.assessment.card.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long referencia;
    @Column(length = 19, nullable = false)
    private String pan;
    @CreationTimestamp
    private LocalDateTime fechaCreacion;
}
