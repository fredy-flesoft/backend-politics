package com.encuestas.electorales.domain.repository;

import com.encuestas.electorales.domain.model.Partido;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de dominio para persistencia de Partidos.
 */
public interface PartidoRepository {
    Partido guardar(Partido partido);
    Optional<Partido> buscarPorId(Long id);
    List<Partido> listarTodos();
    List<Partido> buscarActivos();
    Optional<Partido> buscarPorSigla(String sigla);
    void eliminarPorId(Long id);
    boolean existePorId(Long id);
    boolean existePorSigla(String sigla);
}
