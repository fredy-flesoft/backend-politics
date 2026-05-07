package com.encuestas.electorales.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidad de dominio: Encuesta Electoral.
 */
public class Encuesta {

    private Long id;
    private String titulo;
    private String empresa;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int tamanioMuestra;
    private double margenError;
    private EstadoEncuesta estado;
    private Eleccion eleccion;
    private List<ResultadoEncuesta> resultados;

    public Encuesta() {
        this.resultados = new ArrayList<>();
        this.estado = EstadoEncuesta.PENDIENTE;
    }

    public Encuesta(Long id, String titulo, String empresa, LocalDate fechaInicio,
                    LocalDate fechaFin, int tamanioMuestra, double margenError,
                    EstadoEncuesta estado, Eleccion eleccion) {
        this.id = id;
        this.titulo = titulo;
        this.empresa = empresa;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tamanioMuestra = tamanioMuestra;
        this.margenError = margenError;
        this.estado = estado != null ? estado : EstadoEncuesta.PENDIENTE;
        this.eleccion = eleccion;
        this.resultados = new ArrayList<>();
    }

    // ─── Business Rules ─────────────────────────────────────────────────────────

    public void publicar() {
        if (this.estado == EstadoEncuesta.PUBLICADA) {
            throw new IllegalStateException("La encuesta ya está publicada");
        }
        if (this.resultados.isEmpty()) {
            throw new IllegalStateException("No se puede publicar una encuesta sin resultados");
        }
        this.estado = EstadoEncuesta.PUBLICADA;
    }

    public void iniciarRecoleccion() {
        if (this.estado != EstadoEncuesta.PENDIENTE) {
            throw new IllegalStateException("Solo se puede iniciar una encuesta PENDIENTE");
        }
        this.estado = EstadoEncuesta.EN_PROCESO;
    }

    public void cancelar() {
        if (this.estado == EstadoEncuesta.PUBLICADA) {
            throw new IllegalStateException("No se puede cancelar una encuesta publicada");
        }
        this.estado = EstadoEncuesta.CANCELADA;
    }

    public void agregarResultado(ResultadoEncuesta resultado) {
        if (this.estado == EstadoEncuesta.PUBLICADA) {
            throw new IllegalStateException("No se pueden modificar resultados de una encuesta publicada");
        }
        this.resultados.add(resultado);
    }

    public double getTotalPorcentaje() {
        return resultados.stream().mapToDouble(ResultadoEncuesta::getPorcentaje).sum();
    }

    public boolean estaPublicada() {
        return this.estado == EstadoEncuesta.PUBLICADA;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public int getTamanioMuestra() { return tamanioMuestra; }
    public void setTamanioMuestra(int tamanioMuestra) { this.tamanioMuestra = tamanioMuestra; }

    public double getMargenError() { return margenError; }
    public void setMargenError(double margenError) { this.margenError = margenError; }

    public EstadoEncuesta getEstado() { return estado; }
    public void setEstado(EstadoEncuesta estado) { this.estado = estado; }

    public Eleccion getEleccion() { return eleccion; }
    public void setEleccion(Eleccion eleccion) { this.eleccion = eleccion; }

    public List<ResultadoEncuesta> getResultados() { return Collections.unmodifiableList(resultados); }
    public void setResultados(List<ResultadoEncuesta> resultados) {
        this.resultados = resultados != null ? new ArrayList<>(resultados) : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Encuesta)) return false;
        Encuesta e = (Encuesta) o;
        return Objects.equals(id, e.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Encuesta{id=" + id + ", titulo='" + titulo + "', estado=" + estado + "}";
    }
}
