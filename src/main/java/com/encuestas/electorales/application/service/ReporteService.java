package com.encuestas.electorales.application.service;

import com.encuestas.electorales.application.port.in.GenerarReporteUseCase;
import com.encuestas.electorales.application.port.out.EncuestaPersistencePort;
import com.encuestas.electorales.application.port.out.EleccionPersistencePort;
import com.encuestas.electorales.domain.model.Encuesta;
import com.encuestas.electorales.domain.model.Eleccion;
import com.encuestas.electorales.domain.model.ResultadoEncuesta;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de aplicación: Implementa generación de reportes PDF, Excel y estadísticas.
 */
@Service
@Transactional(readOnly = true)
public class ReporteService implements GenerarReporteUseCase {

    private final EncuestaPersistencePort encuestaPersistence;
    private final EleccionPersistencePort eleccionPersistence;

    public ReporteService(EncuestaPersistencePort encuestaPersistence,
                          EleccionPersistencePort eleccionPersistence) {
        this.encuestaPersistence = encuestaPersistence;
        this.eleccionPersistence = eleccionPersistence;
    }

    @Override
    public byte[] exportarPdf(Long encuestaId) {
        Encuesta encuesta = encuestaPersistence.buscarPorId(encuestaId)
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada con id: " + encuestaId));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            document.open();

            // Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
            Paragraph titulo = new Paragraph("Reporte de Encuesta Electoral", titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20f);
            document.add(titulo);

            // Información de la encuesta
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);

            document.add(new Paragraph("Título: " + encuesta.getTitulo(), normalFont));
            document.add(new Paragraph("Empresa: " + encuesta.getEmpresa(), normalFont));
            document.add(new Paragraph("Fecha: " + encuesta.getFechaInicio() + " - " + encuesta.getFechaFin(), normalFont));
            document.add(new Paragraph("Tamaño de muestra: " + encuesta.getTamanioMuestra(), normalFont));
            document.add(new Paragraph("Margen de error: " + encuesta.getMargenError() + "%", normalFont));
            document.add(new Paragraph("Estado: " + encuesta.getEstado(), normalFont));
            document.add(new Paragraph(" "));

            // Tabla de resultados
            if (!encuesta.getResultados().isEmpty()) {
                document.add(new Paragraph("Resultados:", boldFont));
                document.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{1f, 3f, 2f, 2f});

                // Headers
                String[] headers = {"#", "Candidato", "Porcentaje", "Votos"};
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(header, boldFont));
                    cell.setBackgroundColor(new BaseColor(41, 128, 185));
                    cell.setPadding(8f);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }

                // Datos
                for (ResultadoEncuesta resultado : encuesta.getResultados()) {
                    table.addCell(createCell(String.valueOf(resultado.getPosicion()), normalFont));
                    table.addCell(createCell(
                            resultado.getCandidato() != null ? resultado.getCandidato().getNombreCompleto() : "N/A",
                            normalFont));
                    table.addCell(createCell(String.format("%.2f%%", resultado.getPorcentaje()), normalFont));
                    table.addCell(createCell(String.valueOf(resultado.getVotos()), normalFont));
                }

                document.add(table);
            }

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF: " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] exportarExcel(Long encuestaId) {
        Encuesta encuesta = encuestaPersistence.buscarPorId(encuestaId)
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada con id: " + encuestaId));

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Encuesta - " + encuesta.getTitulo());

            // Estilos
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Info general
            int rowNum = 0;
            createInfoRow(sheet, rowNum++, "Título", encuesta.getTitulo(), headerStyle);
            createInfoRow(sheet, rowNum++, "Empresa", encuesta.getEmpresa(), headerStyle);
            createInfoRow(sheet, rowNum++, "Fecha Inicio", String.valueOf(encuesta.getFechaInicio()), headerStyle);
            createInfoRow(sheet, rowNum++, "Fecha Fin", String.valueOf(encuesta.getFechaFin()), headerStyle);
            createInfoRow(sheet, rowNum++, "Tamaño Muestra", String.valueOf(encuesta.getTamanioMuestra()), headerStyle);
            createInfoRow(sheet, rowNum++, "Margen Error", encuesta.getMargenError() + "%", headerStyle);
            createInfoRow(sheet, rowNum++, "Estado", String.valueOf(encuesta.getEstado()), headerStyle);

            rowNum++; // Línea vacía

            // Headers de resultados
            Row headerRow = sheet.createRow(rowNum++);
            String[] headers = {"Posición", "Candidato", "Partido", "Porcentaje", "Votos"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Datos de resultados
            for (ResultadoEncuesta resultado : encuesta.getResultados()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(resultado.getPosicion());
                row.createCell(1).setCellValue(
                        resultado.getCandidato() != null ? resultado.getCandidato().getNombreCompleto() : "N/A");
                row.createCell(2).setCellValue(
                        resultado.getCandidato() != null && resultado.getCandidato().getPartido() != null
                                ? resultado.getCandidato().getPartido().getNombre() : "N/A");
                row.createCell(3).setCellValue(resultado.getPorcentaje());
                row.createCell(4).setCellValue(resultado.getVotos());
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(baos);
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar Excel: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> generarEstadisticas(Long eleccionId) {
        Eleccion eleccion = eleccionPersistence.buscarPorId(eleccionId)
                .orElseThrow(() -> new RuntimeException("Elección no encontrada con id: " + eleccionId));

        List<Encuesta> encuestas = encuestaPersistence.buscarPorEleccion(eleccionId);

        Map<String, Object> estadisticas = new LinkedHashMap<>();
        estadisticas.put("eleccion", eleccion.getNombre());
        estadisticas.put("tipo", eleccion.getTipo());
        estadisticas.put("estado", eleccion.getEstado().name());
        estadisticas.put("totalEncuestas", encuestas.size());
        estadisticas.put("totalCandidatos", eleccion.getCandidatos().size());

        // Promedio por candidato en todas las encuestas
        Map<String, Double> promedios = new LinkedHashMap<>();
        Map<String, List<Double>> porcentajesPorCandidato = new HashMap<>();

        for (Encuesta encuesta : encuestas) {
            for (ResultadoEncuesta resultado : encuesta.getResultados()) {
                if (resultado.getCandidato() != null) {
                    String nombre = resultado.getCandidato().getNombreCompleto();
                    porcentajesPorCandidato
                            .computeIfAbsent(nombre, k -> new ArrayList<>())
                            .add(resultado.getPorcentaje());
                }
            }
        }

        porcentajesPorCandidato.forEach((nombre, porcentajes) ->
                promedios.put(nombre, porcentajes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0))
        );

        estadisticas.put("promediosPorCandidato", promedios);

        return estadisticas;
    }

    @Override
    public Map<String, Object> generarTendencias(Long eleccionId) {
        List<Encuesta> encuestas = encuestaPersistence.buscarPorEleccion(eleccionId);

        // Ordenar por fecha
        encuestas.sort(Comparator.comparing(Encuesta::getFechaInicio, Comparator.nullsLast(Comparator.naturalOrder())));

        Map<String, Object> tendencias = new LinkedHashMap<>();
        List<Map<String, Object>> datosTemporales = new ArrayList<>();

        for (Encuesta encuesta : encuestas) {
            Map<String, Object> punto = new LinkedHashMap<>();
            punto.put("encuesta", encuesta.getTitulo());
            punto.put("empresa", encuesta.getEmpresa());
            punto.put("fecha", encuesta.getFechaInicio());
            punto.put("tamanioMuestra", encuesta.getTamanioMuestra());

            Map<String, Double> resultadosMap = new LinkedHashMap<>();
            for (ResultadoEncuesta resultado : encuesta.getResultados()) {
                if (resultado.getCandidato() != null) {
                    resultadosMap.put(resultado.getCandidato().getNombreCompleto(), resultado.getPorcentaje());
                }
            }
            punto.put("resultados", resultadosMap);
            datosTemporales.add(punto);
        }

        tendencias.put("totalEncuestas", encuestas.size());
        tendencias.put("tendencias", datosTemporales);

        return tendencias;
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────────

    private PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(6f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private void createInfoRow(Sheet sheet, int rowNum, String label, String value, CellStyle style) {
        Row row = sheet.createRow(rowNum);
        Cell labelCell = row.createCell(0);
        labelCell.setCellValue(label);
        labelCell.setCellStyle(style);
        row.createCell(1).setCellValue(value);
    }
}
