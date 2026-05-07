package com.encuestas.electorales.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidad de dominio: Elección Electoral.
 */
public class Eleccion {

    private Long id;
    private String nombre;
    private String tipo;
    private String descripcion;
    private LocalDate fechaEleccion;
    private EstadoEleccion estado;
    private List<Candidato> candidatos;

    public Eleccion() {
        this.candidatos = new ArrayList<>();
        this.estado = EstadoEleccion.PLANIFICADA;
    }

    public Eleccion(Long id, String nombre, String tipo, String descripcion,
                    LocalDate fechaEleccion, EstadoEleccion estado) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaEleccion = fechaEleccion;
        this.estado = estado != null ? estado : EstadoEleccion.PLANIFICADA;
        this.candidatos = new ArrayList<>();
    }

    // ─── Business Rules ─────────────────────────────────────────────────────────

    public void agregarCandidato(Candidato candidato) {
        if (this.estado == EstadoEleccion.FINALIZADA || this.estado == EstadoEleccion.CANCELADA) {
            throw new IllegalStateException("No se pueden agregar candidatos a una elección " + this.estado);
        }
        if (!this.candidatos.contains(candidato)) {
            this.candidatos.add(candidato);
        }
    }

    public void iniciarProceso() {
        if (this.estado != EstadoEleccion.PLANIFICADA) {
            throw new IllegalStateException("Solo se puede iniciar una elección en estado PLANIFICADA");
        }
        this.estado = EstadoEleccion.EN_PROCESO;
    }

    public void finalizar() {
        if (this.estado != EstadoEleccion.EN_PROCESO) {
            throw new IllegalStateException("Solo se puede finalizar una elección EN_PROCESO");
        }
        this.estado = EstadoEleccion.FINALIZADA;
    }

    public void cancelar() {
        if (this.estado == EstadoEleccion.FINALIZADA) {
            throw new IllegalStateException("No se puede cancelar una elección ya finalizada");
        }
        this.estado = EstadoEleccion.CANCELADA;
    }

    public boolean estaActiva() {
        return this.estado == EstadoEleccion.EN_PROCESO || this.estado == EstadoEleccion.PLANIFICADA;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaEleccion() { return fechaEleccion; }
    public void setFechaEleccion(LocalDate fechaEleccion) { this.fechaEleccion = fechaEleccion; }

    public EstadoEleccion getEstado() { return estado; }
    public void setEstado(EstadoEleccion estado) { this.estado = estado; }

    public List<Candidato> getCandidatos() { return Collections.unmodifiableList(candidatos); }
    public void setCandidatos(List<Candidato> candidatos) {
        this.candidatos = candidatos != null ? new ArrayList<>(candidatos) : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Eleccion)) return false;
        Eleccion e = (Eleccion) o;
        return Objects.equals(id, e.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Eleccion{id=" + id + ", nombre='" + nombre + "', estado=" + estado + "}";
    }
}
