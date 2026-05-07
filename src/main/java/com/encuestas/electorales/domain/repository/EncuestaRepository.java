package com.encuestas.electorales.domain.repository;

import com.encuestas.electorales.domain.model.Encuesta;
import com.encuestas.electorales.domain.model.EstadoEncuesta;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de dominio para persistencia de Encuestas.
 */
public interface EncuestaRepository {
    Encuesta guardar(Encuesta encuesta);
    Optional<Encuesta> buscarPorId(Long id);
    List<Encuesta> listarTodas();
    List<Encuesta> buscarPorEleccion(Long eleccionId);
    List<Encuesta> buscarPorEstado(EstadoEncuesta estado);
    void eliminarPorId(Long id);
    boolean existePorId(Long id);
}
