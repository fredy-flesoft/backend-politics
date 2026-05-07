package com.encuestas.electorales.domain.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidad de dominio: Partido Político.
 * No contiene dependencias de frameworks externos (puro Java).
 */
public class Partido {

    private Long id;
    private String nombre;
    private String sigla;
    private String color;
    private String descripcion;
    private String logoUrl;
    private boolean activo;
    private LocalDate fechaCreacion;

    public Partido() {}

    public Partido(Long id, String nombre, String sigla, String color,
                   String descripcion, String logoUrl, boolean activo, LocalDate fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.sigla = sigla;
        this.color = color;
        this.descripcion = descripcion;
        this.logoUrl = logoUrl;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    }

    // ─── Business Rules ─────────────────────────────────────────────────────────

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }

    public void actualizarDatos(String nombre, String sigla, String color, String descripcion) {
        if (nombre != null && !nombre.isBlank()) this.nombre = nombre;
        if (sigla != null && !sigla.isBlank()) this.sigla = sigla;
        if (color != null && !color.isBlank()) this.color = color;
        if (descripcion != null) this.descripcion = descripcion;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Partido)) return false;
        Partido partido = (Partido) o;
        return Objects.equals(id, partido.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Partido{id=" + id + ", nombre='" + nombre + "', sigla='" + sigla + "'}";
    }
}
