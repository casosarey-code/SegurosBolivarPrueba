package com.example.polizas.service;

import com.example.polizas.dto.RiesgoRequest;
import com.example.polizas.entity.*;
import com.example.polizas.exception.BusinessException;
import com.example.polizas.repository.PolizaRepository;
import com.example.polizas.repository.RiesgoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiesgoService {

    private final RiesgoRepository riesgoRepository;
    private final PolizaRepository polizaRepository;
    private final CoreMockService coreMockService;

    public List<Riesgo> listarPorPoliza(Long polizaId) {
        Poliza p = polizaRepository.findById(polizaId)
                .orElseThrow(() -> new EntityNotFoundException("Póliza no encontrada: " + polizaId));
        return p.getRiesgos();
    }

    @Transactional
    public Riesgo agregar(Long polizaId, RiesgoRequest req) {
        Poliza p = polizaRepository.findById(polizaId)
                .orElseThrow(() -> new EntityNotFoundException("Póliza no encontrada: " + polizaId));

        // Regla: solo Colectiva puede agregar riesgos
        if (p.getTipo() != TipoPoliza.COLECTIVA) {
            throw new BusinessException("Solo se pueden agregar riesgos a pólizas de tipo COLECTIVA");
        }

        if (p.getEstado() == EstadoPoliza.CANCELADA) {
            throw new BusinessException("No se puede agregar riesgo a una póliza cancelada");
        }

        long activos = riesgoRepository.countByPolizaIdAndActivoTrue(polizaId);
        if (p.getTipo() == TipoPoliza.INDIVIDUAL && activos >= 1) {
            throw new BusinessException("Una póliza individual solo puede tener 1 riesgo activo");
        }

        Riesgo r = Riesgo.builder()
                .descripcion(req.getDescripcion())
                .activo(true)
                .poliza(p)
                .build();

        Riesgo saved = riesgoRepository.save(r);
        coreMockService.notificar("ACTUALIZACION", p.getId());
        return saved;
    }

    @Transactional
    public Riesgo cancelar(Long riesgoId) {
        Riesgo r = riesgoRepository.findById(riesgoId)
                .orElseThrow(() -> new EntityNotFoundException("Riesgo no encontrado: " + riesgoId));

        if (!r.getActivo()) {
            throw new BusinessException("El riesgo ya está cancelado");
        }
        r.setActivo(false);
        Riesgo saved = riesgoRepository.save(r);
        coreMockService.notificar("ACTUALIZACION", r.getPoliza().getId());
        return saved;
    }
}