package br.com.ciaf.admin.dtos;

import br.com.ciaf.admin.enums.ImportType;

import java.util.List;

public class ImportMappingSuggestionRequest {
    private ImportType importType;
    private List<String> headers;

    public ImportType getImportType() {
        return importType;
    }

    public void setImportType(ImportType importType) {
        this.importType = importType;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
