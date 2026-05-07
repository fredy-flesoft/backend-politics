package com.encuestas.electorales.application.service;

import com.encuestas.electorales.application.port.in.RegistrarCandidatoUseCase;
import com.encuestas.electorales.application.port.out.CandidatoPersistencePort;
import com.encuestas.electorales.application.port.out.PartidoPersistencePort;
import com.encuestas.electorales.domain.model.Candidato;
import com.encuestas.electorales.domain.model.Partido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación: Implementa casos de uso de Candidatos.
 */
@Service
@Transactional
public class CandidatoService implements RegistrarCandidatoUseCase {

    private final CandidatoPersistencePort candidatoPersistence;
    private final PartidoPersistencePort partidoPersistence;

    public CandidatoService(CandidatoPersistencePort candidatoPersistence,
                            PartidoPersistencePort partidoPersistence) {
        this.candidatoPersistence = candidatoPersistence;
        this.partidoPersistence = partidoPersistence;
    }

    @Override
    public Candidato registrar(Candidato candidato) {
        if (candidatoPersistence.existePorDni(candidato.getDni())) {
            throw new IllegalArgumentException("Ya existe un candidato con DNI: " + candidato.getDni());
        }

        if (candidato.getPartido() != null && candidato.getPartido().getId() != null) {
            Partido partido = partidoPersistence.buscarPorId(candidato.getPartido().getId())
                    .orElseThrow(() -> new RuntimeException("Partido no encontrado"));
            candidato.setPartido(partido);
        }

        candidato.setActivo(true);
        return candidatoPersistence.guardar(candidato);
    }

    @Override
    public Candidato editar(Long id, Candidato datosActualizados) {
        Candidato existente = candidatoPersistence.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado con id: " + id));

        if (datosActualizados.getNombre() != null) existente.setNombre(datosActualizados.getNombre());
        if (datosActualizados.getApellido() != null) existente.setApellido(datosActualizados.getApellido());
        if (datosActualizados.getCargo() != null) existente.setCargo(datosActualizados.getCargo());
        if (datosActualizados.getFotoUrl() != null) existente.setFotoUrl(datosActualizados.getFotoUrl());

        return candidatoPersistence.guardar(existente);
    }

    @Override
    public Candidato asignarPartido(Long candidatoId, Long partidoId) {
        Candidato candidato = candidatoPersistence.buscarPorId(candidatoId)
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado con id: " + candidatoId));

        Partido partido = partidoPersistence.buscarPorId(partidoId)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con id: " + partidoId));

        candidato.asignarPartido(partido);
        return candidatoPersistence.guardar(candidato);
    }

    @Override
    public void eliminar(Long id) {
        if (!candidatoPersistence.existePorId(id)) {
            throw new RuntimeException("Candidato no encontrado con id: " + id);
        }
        candidatoPersistence.eliminarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Candidato> buscarPorId(Long id) {
        return candidatoPersistence.buscarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidato> listarTodos() {
        return candidatoPersistence.listarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidato> listarPorPartido(Long partidoId) {
        return candidatoPersistence.buscarPorPartido(partidoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidato> listarActivos() {
        return candidatoPersistence.buscarActivos();
    }
}
