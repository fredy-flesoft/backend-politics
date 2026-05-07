package com.encuestas.electorales.application.port.out;

import com.encuestas.electorales.domain.model.Encuesta;
import com.encuestas.electorales.domain.model.EstadoEncuesta;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Persistencia de Encuestas.
 */
public interface EncuestaPersistencePort {
    Encuesta guardar(Encuesta encuesta);
    Optional<Encuesta> buscarPorId(Long id);
    List<Encuesta> listarTodas();
    List<Encuesta> buscarPorEleccion(Long eleccionId);
    List<Encuesta> buscarPorEstado(EstadoEncuesta estado);
    void eliminarPorId(Long id);
    boolean existePorId(Long id);
}
