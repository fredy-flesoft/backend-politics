package com.encuestas.electorales.application.port.out;

import com.encuestas.electorales.domain.model.Candidato;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Persistencia de Candidatos.
 */
public interface CandidatoPersistencePort {
    Candidato guardar(Candidato candidato);
    Optional<Candidato> buscarPorId(Long id);
    List<Candidato> listarTodos();
    List<Candidato> buscarPorPartido(Long partidoId);
    List<Candidato> buscarActivos();
    Optional<Candidato> buscarPorDni(String dni);
    void eliminarPorId(Long id);
    boolean existePorId(Long id);
    boolean existePorDni(String dni);
}
