package com.encuestas.electorales.application.service;

import com.encuestas.electorales.application.port.in.RegistrarPartidoUseCase;
import com.encuestas.electorales.application.port.out.PartidoPersistencePort;
import com.encuestas.electorales.domain.model.Partido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación: Implementa casos de uso de Partidos.
 */
@Service
@Transactional
public class PartidoService implements RegistrarPartidoUseCase {

    private final PartidoPersistencePort persistencePort;

    public PartidoService(PartidoPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Partido registrar(Partido partido) {
        if (persistencePort.existePorSigla(partido.getSigla())) {
            throw new IllegalArgumentException("Ya existe un partido con la sigla: " + partido.getSigla());
        }
        partido.setActivo(true);
        partido.setFechaCreacion(LocalDate.now());
        return persistencePort.guardar(partido);
    }

    @Override
    public Partido editar(Long id, Partido datosActualizados) {
        Partido existente = persistencePort.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Partido no encontrado con id: " + id));

        existente.actualizarDatos(
                datosActualizados.getNombre(),
                datosActualizados.getSigla(),
                datosActualizados.getColor(),
                datosActualizados.getDescripcion()
        );

        if (datosActualizados.getLogoUrl() != null) {
            existente.setLogoUrl(datosActualizados.getLogoUrl());
        }

        return persistencePort.guardar(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!persistencePort.existePorId(id)) {
            throw new RuntimeException("Partido no encontrado con id: " + id);
        }
        persistencePort.eliminarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Partido> buscarPorId(Long id) {
        return persistencePort.buscarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Partido> listarTodos() {
        return persistencePort.listarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Partido> listarActivos() {
        return persistencePort.buscarActivos();
    }
}
