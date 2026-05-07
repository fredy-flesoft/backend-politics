package com.encuestas.electorales.application.port.out;

/**
 * Puerto de salida: Generación de Reportes.
 */
public interface ReportePersistencePort {
    byte[] generarPdfEncuesta(Long encuestaId);
    byte[] generarExcelEncuesta(Long encuestaId);
}
