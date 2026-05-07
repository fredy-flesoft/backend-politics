package com.encuestas.electorales.application.port.in;

import com.encuestas.electorales.domain.model.Eleccion;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada: Casos de uso para Elecciones.
 */
public interface RegistrarEleccionUseCase {
    Eleccion crear(Eleccion eleccion);
    Eleccion editar(Long id, Eleccion eleccion);
    void eliminar(Long id);
    Optional<Eleccion> buscarPorId(Long id);
    List<Eleccion> listarTodas();
    Eleccion iniciarProceso(Long id);
    Eleccion finalizar(Long id);
    Eleccion cancelar(Long id);
    Eleccion agregarCandidato(Long eleccionId, Long candidatoId);
}
