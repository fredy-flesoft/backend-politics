package com.encuestas.electorales.domain.model;

import java.util.Objects;

/**
 * Entidad de dominio: Candidato Electoral.
 */
public class Candidato {

    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String cargo;
    private String fotoUrl;
    private boolean activo;
    private Partido partido;

    public Candidato() {}

    public Candidato(Long id, String nombre, String apellido, String dni,
                     String cargo, String fotoUrl, boolean activo, Partido partido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.cargo = cargo;
        this.fotoUrl = fotoUrl;
        this.activo = activo;
        this.partido = partido;
    }

    // ─── Business Rules ─────────────────────────────────────────────────────────

    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    public void asignarPartido(Partido partido) {
        if (partido == null) throw new IllegalArgumentException("El partido no puede ser nulo");
        this.partido = partido;
    }

    public void activar() { this.activo = true; }
    public void desactivar() { this.activo = false; }

    // ─── Getters & Setters ───────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public Partido getPartido() { return partido; }
    public void setPartido(Partido partido) { this.partido = partido; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Candidato)) return false;
        Candidato c = (Candidato) o;
        return Objects.equals(id, c.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Candidato{id=" + id + ", nombre='" + getNombreCompleto() + "', cargo='" + cargo + "'}";
    }
}
