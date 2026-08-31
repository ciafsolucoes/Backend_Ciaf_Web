package br.com.ciaf.admin.services;

import br.com.ciaf.admin.dtos.ImportPreviewResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class SpreadsheetReaderService {

    public ImportPreviewResponse readPreview(MultipartFile file, int previewLimit) throws Exception {
        String filename = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();

        if (filename.endsWith(".csv")) {
            return readCsv(file, previewLimit);
        }

        if (filename.endsWith(".xlsx")) {
            return readXlsx(file, previewLimit);
        }

        throw new IllegalArgumentException("Formato de arquivo não suportado. Use .csv ou .xlsx");
    }

    private ImportPreviewResponse readCsv(MultipartFile file, int previewLimit) throws Exception {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader)) {

            List<String> headers = new ArrayList<>(parser.getHeaderMap().keySet());
            List<Map<String, String>> rows = new ArrayList<>();

            int count = 0;
            for (CSVRecord record : parser) {
                Map<String, String> row = new LinkedHashMap<>();
                for (String header : headers) {
                    row.put(header, record.get(header));
                }
                rows.add(row);
                count++;
                if (count >= previewLimit) break;
            }

            return new ImportPreviewResponse(headers, rows);
        }
    }

    private ImportPreviewResponse readXlsx(MultipartFile file, int previewLimit) throws Exception {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) {
                return new ImportPreviewResponse(List.of(), List.of());
            }

            Row headerRow = rowIterator.next();
            List<String> headers = new ArrayList<>();

            for (Cell cell : headerRow) {
                headers.add(getCellAsString(cell));
            }

            List<Map<String, String>> rows = new ArrayList<>();
            int count = 0;

            while (rowIterator.hasNext() && count < previewLimit) {
                Row rowObj = rowIterator.next();
                Map<String, String> row = new LinkedHashMap<>();

                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = rowObj.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    row.put(headers.get(i), getCellAsString(cell));
                }

                rows.add(row);
                count++;
            }

            return new ImportPreviewResponse(headers, rows);
        }
    }

    private String getCellAsString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getLocalDateTimeCellValue().toString();
                }
                yield DataFormatterHolder.FORMATTER.formatCellValue(cell);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> DataFormatterHolder.FORMATTER.formatCellValue(cell);
            case BLANK, _NONE, ERROR -> "";
        };
    }

    private static class DataFormatterHolder {
        private static final DataFormatter FORMATTER = new DataFormatter();
    }
}