package com.encuestas.electorales.application.port.in;

import com.encuestas.electorales.domain.model.Candidato;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada: Casos de uso para Candidatos.
 */
public interface RegistrarCandidatoUseCase {
    Candidato registrar(Candidato candidato);
    Candidato editar(Long id, Candidato candidato);
    Candidato asignarPartido(Long candidatoId, Long partidoId);
    void eliminar(Long id);
    Optional<Candidato> buscarPorId(Long id);
    List<Candidato> listarTodos();
    List<Candidato> listarPorPartido(Long partidoId);
    List<Candidato> listarActivos();
}
