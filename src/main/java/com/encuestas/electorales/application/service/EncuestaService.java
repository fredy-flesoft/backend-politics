package com.encuestas.electorales.application.service;

import com.encuestas.electorales.application.port.in.RegistrarEncuestaUseCase;
import com.encuestas.electorales.application.port.out.CandidatoPersistencePort;
import com.encuestas.electorales.application.port.out.EncuestaPersistencePort;
import com.encuestas.electorales.application.port.out.EleccionPersistencePort;
import com.encuestas.electorales.domain.model.Candidato;
import com.encuestas.electorales.domain.model.Eleccion;
import com.encuestas.electorales.domain.model.Encuesta;
import com.encuestas.electorales.domain.model.ResultadoEncuesta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación: Implementa casos de uso de Encuestas.
 */
@Service
@Transactional
public class EncuestaService implements RegistrarEncuestaUseCase {

    private final EncuestaPersistencePort encuestaPersistence;
    private final EleccionPersistencePort eleccionPersistence;
    private final CandidatoPersistencePort candidatoPersistence;

    public EncuestaService(EncuestaPersistencePort encuestaPersistence,
                           EleccionPersistencePort eleccionPersistence,
                           CandidatoPersistencePort candidatoPersistence) {
        this.encuestaPersistence = encuestaPersistence;
        this.eleccionPersistence = eleccionPersistence;
        this.candidatoPersistence = candidatoPersistence;
    }

    @Override
    public Encuesta registrar(Encuesta encuesta) {
        if (encuesta.getEleccion() != null && encuesta.getEleccion().getId() != null) {
            Eleccion eleccion = eleccionPersistence.buscarPorId(encuesta.getEleccion().getId())
                    .orElseThrow(() -> new RuntimeException("Elección no encontrada"));
            encuesta.setEleccion(eleccion);
        }
        return encuestaPersistence.guardar(encuesta);
    }

    @Override
    public Encuesta editar(Long id, Encuesta datosActualizados) {
        Encuesta existente = encuestaPersistence.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada con id: " + id));

        if (existente.estaPublicada()) {
            throw new IllegalStateException("No se puede editar una encuesta ya publicada");
        }

        if (datosActualizados.getTitulo() != null) existente.setTitulo(datosActualizados.getTitulo());
        if (datosActualizados.getEmpresa() != null) existente.setEmpresa(datosActualizados.getEmpresa());
        if (datosActualizados.getFechaInicio() != null) existente.setFechaInicio(datosActualizados.getFechaInicio());
        if (datosActualizados.getFechaFin() != null) existente.setFechaFin(datosActualizados.getFechaFin());
        if (datosActualizados.getTamanioMuestra() > 0) existente.setTamanioMuestra(datosActualizados.getTamanioMuestra());
        if (datosActualizados.getMargenError() > 0) existente.setMargenError(datosActualizados.getMargenError());

        return encuestaPersistence.guardar(existente);
    }

    @Override
    public void eliminar(Long id) {
        Encuesta encuesta = encuestaPersistence.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada con id: " + id));

        if (encuesta.estaPublicada()) {
            throw new IllegalStateException("No se puede eliminar una encuesta publicada");
        }
        encuestaPersistence.eliminarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Encuesta> buscarPorId(Long id) {
        return encuestaPersistence.buscarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Encuesta> listarTodas() {
        return encuestaPersistence.listarTodas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Encuesta> listarPorEleccion(Long eleccionId) {
        return encuestaPersistence.buscarPorEleccion(eleccionId);
    }

    @Override
    public Encuesta agregarResultado(Long encuestaId, ResultadoEncuesta resultado) {
        Encuesta encuesta = encuestaPersistence.buscarPorId(encuestaId)
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada con id: " + encuestaId));

        if (resultado.getCandidato() != null && resultado.getCandidato().getId() != null) {
            Candidato candidato = candidatoPersistence.buscarPorId(resultado.getCandidato().getId())
                    .orElseThrow(() -> new RuntimeException("Candidato no encontrado"));
            resultado.setCandidato(candidato);
        }

        encuesta.agregarResultado(resultado);
        return encuestaPersistence.guardar(encuesta);
    }

    @Override
    public Encuesta publicar(Long encuestaId) {
        Encuesta encuesta = encuestaPersistence.buscarPorId(encuestaId)
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada con id: " + encuestaId));
        encuesta.publicar();
        return encuestaPersistence.guardar(encuesta);
    }
}
