package com.encuestas.electorales.application.port.in;

import java.util.Map;

/**
 * Puerto de entrada: Casos de uso para Reportes.
 */
public interface GenerarReporteUseCase {
    byte[] exportarPdf(Long encuestaId);
    byte[] exportarExcel(Long encuestaId);
    Map<String, Object> generarEstadisticas(Long eleccionId);
    Map<String, Object> generarTendencias(Long eleccionId);
}
