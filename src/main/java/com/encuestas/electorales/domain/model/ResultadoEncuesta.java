package com.encuestas.electorales.domain.model;

import java.util.Objects;

/**
 * Entidad de dominio: Resultado de una Encuesta.
 */
public class ResultadoEncuesta {

    private Long id;
    private Candidato candidato;
    private double porcentaje;
    private int votos;
    private int posicion;

    public ResultadoEncuesta() {}

    public ResultadoEncuesta(Long id, Candidato candidato, double porcentaje, int votos, int posicion) {
        this.id = id;
        this.candidato = candidato;
        this.porcentaje = porcentaje;
        this.votos = votos;
        this.posicion = posicion;
    }

    // ─── Business Rules ─────────────────────────────────────────────────────────

    public boolean esGanador(int totalCandidatos) {
        return this.posicion == 1 && totalCandidatos > 1;
    }

    public boolean superaMargen(double margenError) {
        return this.porcentaje > margenError;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Candidato getCandidato() { return candidato; }
    public void setCandidato(Candidato candidato) { this.candidato = candidato; }

    public double getPorcentaje() { return porcentaje; }
    public void setPorcentaje(double porcentaje) { this.porcentaje = porcentaje; }

    public int getVotos() { return votos; }
    public void setVotos(int votos) { this.votos = votos; }

    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResultadoEncuesta)) return false;
        ResultadoEncuesta r = (ResultadoEncuesta) o;
        return Objects.equals(id, r.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "ResultadoEncuesta{id=" + id + ", porcentaje=" + porcentaje + ", posicion=" + posicion + "}";
    }
}
