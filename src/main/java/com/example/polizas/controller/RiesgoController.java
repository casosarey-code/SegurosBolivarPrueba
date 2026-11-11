package com.example.polizas.controller;

import com.example.polizas.dto.RiesgoRequest;
import com.example.polizas.entity.Riesgo;
import com.example.polizas.service.RiesgoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RiesgoController {

    private final RiesgoService riesgoService;

    // 2. GET /polizas/{id}/riesgos
    @GetMapping("/polizas/{id}/riesgos")
    public List<Riesgo> listarPorPoliza(@PathVariable Long id) {
        return riesgoService.listarPorPoliza(id);
    }

    // 5. POST /polizas/{id}/riesgos  (solo si tipo = Colectiva)
    @PostMapping("/polizas/{id}/riesgos")
    public Riesgo agregar(@PathVariable Long id, @Valid @RequestBody RiesgoRequest req) {
        return riesgoService.agregar(id, req);
    }

    // 6. POST /riesgos/{id}/cancelar
    @PostMapping("/riesgos/{id}/cancelar")
    public Riesgo cancelar(@PathVariable Long id) {
        return riesgoService.cancelar(id);
    }
}