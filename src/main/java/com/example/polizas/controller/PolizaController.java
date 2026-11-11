package com.example.polizas.controller;

import com.example.polizas.dto.PolizaRequest;
import com.example.polizas.entity.*;
import com.example.polizas.service.PolizaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polizas")
@RequiredArgsConstructor
public class PolizaController {

    private final PolizaService polizaService;

    // 1. GET /polizas?tipo=...&estado=...
    @GetMapping
    public List<Poliza> listar(@RequestParam(required = false) TipoPoliza tipo,
                               @RequestParam(required = false) EstadoPoliza estado) {
        return polizaService.listar(tipo, estado);
    }

    // Extra (opcional): crear póliza para pruebas
    @PostMapping
    public ResponseEntity<Poliza> crear(@Valid @RequestBody PolizaRequest req) {
        return ResponseEntity.ok(polizaService.crear(req));
    }

    // 3. POST /polizas/{id}/renovar
    @PostMapping("/{id}/renovar")
    public Poliza renovar(@PathVariable Long id) {
        return polizaService.renovar(id);
    }

    // 4. POST /polizas/{id}/cancelar
    @PostMapping("/{id}/cancelar")
    public Poliza cancelar(@PathVariable Long id) {
        return polizaService.cancelar(id);
    }
}