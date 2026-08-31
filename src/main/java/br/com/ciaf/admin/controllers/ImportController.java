package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.dtos.ColumnMatchSuggestion;
import br.com.ciaf.admin.dtos.ImportCommitRequest;
import br.com.ciaf.admin.dtos.ImportMappingSuggestionRequest;
import br.com.ciaf.admin.dtos.ImportPreviewResponse;
import br.com.ciaf.admin.enums.ImportType;
import br.com.ciaf.admin.services.ColumnSimilarityService;
import br.com.ciaf.admin.services.ImportExecutionService;
import br.com.ciaf.admin.services.SpreadsheetReaderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/import")
public class ImportController {

    private final SpreadsheetReaderService spreadsheetReaderService;
    private final ColumnSimilarityService columnSimilarityService;
    private final ImportExecutionService importExecutionService;

    public ImportController(SpreadsheetReaderService spreadsheetReaderService,
                            ColumnSimilarityService columnSimilarityService, ImportExecutionService importExecutionService) {
        this.spreadsheetReaderService = spreadsheetReaderService;
        this.columnSimilarityService = columnSimilarityService;
        this.importExecutionService = importExecutionService;
    }

    @PostMapping(value = "/preview", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ImportPreviewResponse preview(@RequestParam("file") MultipartFile file) throws Exception {
        return spreadsheetReaderService.readPreview(file, 20);
    }

    @PostMapping("/suggest")
    public List<ColumnMatchSuggestion> suggest(@RequestBody ImportMappingSuggestionRequest request) {
        ImportType importType = request.getImportType();
        return columnSimilarityService.suggest(importType, request.getHeaders());
    }

    @PostMapping("/commit")
    public ResponseEntity<?> commit(@RequestBody ImportCommitRequest request) {

        List<Map<String, String>> mappedRows = importExecutionService.remapRows(request);
        importExecutionService.executeImport(request.getImportType(), mappedRows, request.getEmitenteId());
        return ResponseEntity.ok().build();
    }
}