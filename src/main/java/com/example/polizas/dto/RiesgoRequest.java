package com.example.polizas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RiesgoRequest {
    @NotBlank
    private String descripcion;
}