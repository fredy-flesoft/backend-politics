package com.encuestas.electorales.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "partidos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PartidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, unique = true, length = 20)
    private String sigla;

    @Column(length = 20)
    private String color;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(nullable = false)
    private boolean activo;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;
}
