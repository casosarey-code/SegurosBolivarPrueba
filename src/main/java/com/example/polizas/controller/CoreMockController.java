package com.example.polizas.controller;

import com.example.polizas.dto.EventoRequest;
import com.example.polizas.service.CoreMockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/core-mock")
@RequiredArgsConstructor
public class CoreMockController {

    private final CoreMockService coreMockService;

    // POST /core-mock/evento
    @PostMapping("/evento")
    public Map<String, Object> evento(@Valid @RequestBody EventoRequest req) {
        coreMockService.notificar(req.getEvento(), req.getPolizaId());
        return Map.of(
                "status", "OK",
                "mensaje", "Evento registrado en logs (simulación CORE)",
                "evento", req.getEvento(),
                "polizaId", req.getPolizaId()
        );
    }
}