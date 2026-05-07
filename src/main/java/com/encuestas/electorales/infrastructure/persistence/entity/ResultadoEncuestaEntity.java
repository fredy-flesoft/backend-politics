package com.encuestas.electorales.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resultados_encuesta")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ResultadoEncuestaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidato_id")
    private CandidatoEntity candidato;

    @Column(nullable = false)
    private double porcentaje;

    @Column(nullable = false)
    private int votos;

    @Column(nullable = false)
    private int posicion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encuesta_id", nullable = false)
    private EncuestaEntity encuesta;
}
