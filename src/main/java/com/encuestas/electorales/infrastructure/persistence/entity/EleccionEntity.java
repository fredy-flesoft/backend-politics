package com.encuestas.electorales.infrastructure.persistence.entity;

import com.encuestas.electorales.domain.model.EstadoEleccion;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "elecciones")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EleccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(length = 50)
    private String tipo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_eleccion")
    private LocalDate fechaEleccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoEleccion estado;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "eleccion_candidatos",
            joinColumns = @JoinColumn(name = "eleccion_id"),
            inverseJoinColumns = @JoinColumn(name = "candidato_id")
    )
    @Builder.Default
    private List<CandidatoEntity> candidatos = new ArrayList<>();
}
