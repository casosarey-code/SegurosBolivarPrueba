package com.example.polizas.dto;

import com.example.polizas.entity.TipoPoliza;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PolizaRequest {
    @NotNull
    private TipoPoliza tipo;
    @NotNull
    private BigDecimal canon;
    @NotNull
    private BigDecimal prima;
}