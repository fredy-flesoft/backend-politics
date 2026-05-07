package com.encuestas.electorales.application.port.in;

import com.encuestas.electorales.domain.model.Encuesta;
import com.encuestas.electorales.domain.model.ResultadoEncuesta;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada: Casos de uso para Encuestas.
 */
public interface RegistrarEncuestaUseCase {
    Encuesta registrar(Encuesta encuesta);
    Encuesta editar(Long id, Encuesta encuesta);
    void eliminar(Long id);
    Optional<Encuesta> buscarPorId(Long id);
    List<Encuesta> listarTodas();
    List<Encuesta> listarPorEleccion(Long eleccionId);
    Encuesta agregarResultado(Long encuestaId, ResultadoEncuesta resultado);
    Encuesta publicar(Long encuestaId);
}
