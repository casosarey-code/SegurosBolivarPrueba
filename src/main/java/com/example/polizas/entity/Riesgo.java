package com.example.polizas.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "riesgos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Riesgo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Boolean activo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "poliza_id", nullable = false)
    private Poliza poliza;
}
