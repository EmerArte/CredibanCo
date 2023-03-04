package com.credibanco.assessment.card.model.repository;

import com.credibanco.assessment.card.model.TarjetaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaRepository extends JpaRepository<TarjetaModel, Long> {
}
