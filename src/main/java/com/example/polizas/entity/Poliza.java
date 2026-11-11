package com.example.polizas.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polizas")
public class Poliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPoliza tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPoliza estado;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal canon;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal prima;

    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Riesgo> riesgos = new ArrayList<>();

    // Constructor vacío
    public Poliza() {}

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoPoliza getTipo() { return tipo; }
    public void setTipo(TipoPoliza tipo) { this.tipo = tipo; }

    public EstadoPoliza getEstado() { return estado; }
    public void setEstado(EstadoPoliza estado) { this.estado = estado; }

    public BigDecimal getCanon() { return canon; }
    public void setCanon(BigDecimal canon) { this.canon = canon; }

    public BigDecimal getPrima() { return prima; }
    public void setPrima(BigDecimal prima) { this.prima = prima; }

    public List<Riesgo> getRiesgos() { return riesgos; }
    public void setRiesgos(List<Riesgo> riesgos) { this.riesgos = riesgos; }

    // Builder estático
    public static PolizaBuilder builder() { return new PolizaBuilder(); }

    public static class PolizaBuilder {
        private TipoPoliza tipo;
        private EstadoPoliza estado;
        private BigDecimal canon;
        private BigDecimal prima;

        public PolizaBuilder tipo(TipoPoliza tipo) { this.tipo = tipo; return this; }
        public PolizaBuilder estado(EstadoPoliza estado) { this.estado = estado; return this; }
        public PolizaBuilder canon(BigDecimal canon) { this.canon = canon; return this; }
        public PolizaBuilder prima(BigDecimal prima) { this.prima = prima; return this; }

        public Poliza build() {
            Poliza p = new Poliza();
            p.tipo = this.tipo;
            p.estado = this.estado;
            p.canon = this.canon;
            p.prima = this.prima;
            return p;
        }
    }
}