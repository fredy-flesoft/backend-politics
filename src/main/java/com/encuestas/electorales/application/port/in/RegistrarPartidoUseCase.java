package com.encuestas.electorales.application.port.in;

import com.encuestas.electorales.domain.model.Partido;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada: Casos de uso para Partidos Políticos.
 */
public interface RegistrarPartidoUseCase {
    Partido registrar(Partido partido);
    Partido editar(Long id, Partido partido);
    void eliminar(Long id);
    Optional<Partido> buscarPorId(Long id);
    List<Partido> listarTodos();
    List<Partido> listarActivos();
}
