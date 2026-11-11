package com.example.polizas.repository;

import com.example.polizas.entity.Riesgo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiesgoRepository extends JpaRepository<Riesgo, Long> {
    long countByPolizaIdAndActivoTrue(Long polizaId);
}