package com.encuestas.electorales.infrastructure.persistence.entity;

import com.encuestas.electorales.domain.model.EstadoEncuesta;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "encuestas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EncuestaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(length = 150)
    private String empresa;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "tamanio_muestra")
    private int tamanioMuestra;

    @Column(name = "margen_error")
    private double margenError;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoEncuesta estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eleccion_id")
    private EleccionEntity eleccion;

    @OneToMany(mappedBy = "encuesta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ResultadoEncuestaEntity> resultados = new ArrayList<>();
}
