package com.encuestas.electorales.application.port.out;

import com.encuestas.electorales.domain.model.Partido;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Persistencia de Partidos.
 */
public interface PartidoPersistencePort {
    Partido guardar(Partido partido);
    Optional<Partido> buscarPorId(Long id);
    List<Partido> listarTodos();
    List<Partido> buscarActivos();
    Optional<Partido> buscarPorSigla(String sigla);
    void eliminarPorId(Long id);
    boolean existePorId(Long id);
    boolean existePorSigla(String sigla);
}
