package com.example.polizas.config;

import com.example.polizas.entity.*;
import com.example.polizas.repository.PolizaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(PolizaRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(Poliza.builder()
                        .tipo(TipoPoliza.INDIVIDUAL)
                        .estado(EstadoPoliza.VIGENTE)
                        .canon(new BigDecimal("1000.00"))
                        .prima(new BigDecimal("200.00"))
                        .build());

                repo.save(Poliza.builder()
                        .tipo(TipoPoliza.COLECTIVA)
                        .estado(EstadoPoliza.VIGENTE)
                        .canon(new BigDecimal("5000.00"))
                        .prima(new BigDecimal("800.00"))
                        .build());
            }
        };
    }
}