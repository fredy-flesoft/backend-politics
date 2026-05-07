package com.encuestas.electorales.domain.repository;

import com.encuestas.electorales.domain.model.Eleccion;
import com.encuestas.electorales.domain.model.EstadoEleccion;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de dominio para persistencia de Elecciones.
 */
public interface EleccionRepository {
    Eleccion guardar(Eleccion eleccion);
    Optional<Eleccion> buscarPorId(Long id);
    List<Eleccion> listarTodas();
    List<Eleccion> buscarPorEstado(EstadoEleccion estado);
    void eliminarPorId(Long id);
    boolean existePorId(Long id);
}
