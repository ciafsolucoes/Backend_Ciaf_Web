package br.com.ciaf.admin.dtos;

import br.com.ciaf.admin.enums.ImportType;

import java.util.Map;

public class ImportCommitRequest {
    private ImportType importType;
    private Long emitenteId;
    private Map<String, String> mapping;
    private java.util.List<Map<String, String>> rows;

    public ImportType getImportType() {
        return importType;
    }

    public void setImportType(ImportType importType) {
        this.importType = importType;
    }

    public Long getEmitenteId() {
        return emitenteId;
    }

    public void setEmitenteId(Long emitenteId) {
        this.emitenteId = emitenteId;
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }

    public java.util.List<Map<String, String>> getRows() {
        return rows;
    }

    public void setRows(java.util.List<Map<String, String>> rows) {
        this.rows = rows;
    }
}
