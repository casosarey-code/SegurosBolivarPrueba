package com.example.polizas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventoRequest {
    @NotBlank
    private String evento;
    @NotNull
    private Long polizaId;
}