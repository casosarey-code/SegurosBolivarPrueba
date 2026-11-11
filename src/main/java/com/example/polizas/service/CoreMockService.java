package com.example.polizas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoreMockService {

    /**
     * Simula el envío al CORE. Su único propósito es registrar en logs.
     */
    public void notificar(String evento, Long polizaId) {
        log.info("[CORE-MOCK] Intento de envío al CORE → evento={}, polizaId={}", evento, polizaId);
    }
}