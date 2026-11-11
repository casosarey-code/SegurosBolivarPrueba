package com.example.polizas.service;

import com.example.polizas.dto.PolizaRequest;
import com.example.polizas.entity.*;
import com.example.polizas.exception.BusinessException;
import com.example.polizas.repository.PolizaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PolizaService {

    private final PolizaRepository polizaRepository;
    private final CoreMockService coreMockService;

    @Value("${app.ipc}")
    private BigDecimal ipc;

    public List<Poliza> listar(TipoPoliza tipo, EstadoPoliza estado) {
        if (tipo != null && estado != null) return polizaRepository.findByTipoAndEstado(tipo, estado);
        if (tipo != null) return polizaRepository.findByTipo(tipo);
        if (estado != null) return polizaRepository.findByEstado(estado);
        return polizaRepository.findAll();
    }

    public Poliza obtener(Long id) {
        return polizaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Póliza no encontrada: " + id));
    }

    @Transactional
    public Poliza renovar(Long id) {
        Poliza p = obtener(id);

        if (p.getEstado() == EstadoPoliza.CANCELADA) {
            throw new BusinessException("No se puede renovar una póliza cancelada");
        }

        BigDecimal factor = BigDecimal.ONE.add(ipc);
        p.setCanon(p.getCanon().multiply(factor).setScale(2, RoundingMode.HALF_UP));
        p.setPrima(p.getPrima().multiply(factor).setScale(2, RoundingMode.HALF_UP));
        p.setEstado(EstadoPoliza.RENOVADA);

        Poliza saved = polizaRepository.save(p);
        coreMockService.notificar("ACTUALIZACION", saved.getId());
        return saved;
    }

    @Transactional
    public Poliza cancelar(Long id) {
        Poliza p = obtener(id);

        if (p.getEstado() == EstadoPoliza.CANCELADA) {
            throw new BusinessException("La póliza ya se encuentra cancelada");
        }

        p.setEstado(EstadoPoliza.CANCELADA);
        // La cancelación de la póliza cancela todos sus riesgos
        p.getRiesgos().forEach(r -> r.setActivo(false));

        Poliza saved = polizaRepository.save(p);
        coreMockService.notificar("ACTUALIZACION", saved.getId());
        return saved;
    }

    // Extra (no requerido en endpoints pero útil para pruebas)
    public Poliza crear(PolizaRequest req) {
        Poliza p = Poliza.builder()
                .tipo(req.getTipo())
                .estado(EstadoPoliza.VIGENTE)
                .canon(req.getCanon())
                .prima(req.getPrima())
                .build();
        return polizaRepository.save(p);
    }
}