package com.encuestas.electorales.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "candidatos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CandidatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true, length = 15)
    private String dni;

    @Column(length = 100)
    private String cargo;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(nullable = false)
    private boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partido_id")
    private PartidoEntity partido;
}
