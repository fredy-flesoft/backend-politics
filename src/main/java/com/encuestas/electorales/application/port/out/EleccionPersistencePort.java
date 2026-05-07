package com.encuestas.electorales.application.port.out;

import com.encuestas.electorales.domain.model.Eleccion;
import com.encuestas.electorales.domain.model.EstadoEleccion;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Persistencia de Elecciones.
 */
public interface EleccionPersistencePort {
    Eleccion guardar(Eleccion eleccion);
    Optional<Eleccion> buscarPorId(Long id);
    List<Eleccion> listarTodas();
    List<Eleccion> buscarPorEstado(EstadoEleccion estado);
    void eliminarPorId(Long id);
    boolean existePorId(Long id);
}
