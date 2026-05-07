package com.encuestas.electorales.application.service;

import com.encuestas.electorales.application.port.in.RegistrarEleccionUseCase;
import com.encuestas.electorales.application.port.out.CandidatoPersistencePort;
import com.encuestas.electorales.application.port.out.EleccionPersistencePort;
import com.encuestas.electorales.domain.model.Candidato;
import com.encuestas.electorales.domain.model.Eleccion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación: Implementa casos de uso de Elecciones.
 */
@Service
@Transactional
public class EleccionService implements RegistrarEleccionUseCase {

    private final EleccionPersistencePort eleccionPersistence;
    private final CandidatoPersistencePort candidatoPersistence;

    public EleccionService(EleccionPersistencePort eleccionPersistence,
                           CandidatoPersistencePort candidatoPersistence) {
        this.eleccionPersistence = eleccionPersistence;
        this.candidatoPersistence = candidatoPersistence;
    }

    @Override
    public Eleccion crear(Eleccion eleccion) {
        return eleccionPersistence.guardar(eleccion);
    }

    @Override
    public Eleccion editar(Long id, Eleccion datosActualizados) {
        Eleccion existente = eleccionPersistence.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Elección no encontrada con id: " + id));

        if (datosActualizados.getNombre() != null) existente.setNombre(datosActualizados.getNombre());
        if (datosActualizados.getTipo() != null) existente.setTipo(datosActualizados.getTipo());
        if (datosActualizados.getDescripcion() != null) existente.setDescripcion(datosActualizados.getDescripcion());
        if (datosActualizados.getFechaEleccion() != null) existente.setFechaEleccion(datosActualizados.getFechaEleccion());

        return eleccionPersistence.guardar(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!eleccionPersistence.existePorId(id)) {
            throw new RuntimeException("Elección no encontrada con id: " + id);
        }
        eleccionPersistence.eliminarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Eleccion> buscarPorId(Long id) {
        return eleccionPersistence.buscarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Eleccion> listarTodas() {
        return eleccionPersistence.listarTodas();
    }

    @Override
    public Eleccion iniciarProceso(Long id) {
        Eleccion eleccion = eleccionPersistence.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Elección no encontrada con id: " + id));
        eleccion.iniciarProceso();
        return eleccionPersistence.guardar(eleccion);
    }

    @Override
    public Eleccion finalizar(Long id) {
        Eleccion eleccion = eleccionPersistence.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Elección no encontrada con id: " + id));
        eleccion.finalizar();
        return eleccionPersistence.guardar(eleccion);
    }

    @Override
    public Eleccion cancelar(Long id) {
        Eleccion eleccion = eleccionPersistence.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Elección no encontrada con id: " + id));
        eleccion.cancelar();
        return eleccionPersistence.guardar(eleccion);
    }

    @Override
    public Eleccion agregarCandidato(Long eleccionId, Long candidatoId) {
        Eleccion eleccion = eleccionPersistence.buscarPorId(eleccionId)
                .orElseThrow(() -> new RuntimeException("Elección no encontrada con id: " + eleccionId));

        Candidato candidato = candidatoPersistence.buscarPorId(candidatoId)
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado con id: " + candidatoId));

        eleccion.agregarCandidato(candidato);
        return eleccionPersistence.guardar(eleccion);
    }
}
