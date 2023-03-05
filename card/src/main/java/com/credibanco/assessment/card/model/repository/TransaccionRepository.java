package com.credibanco.assessment.card.model.repository;

import com.credibanco.assessment.card.model.TransaccionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<TransaccionModel, Long> {
}
