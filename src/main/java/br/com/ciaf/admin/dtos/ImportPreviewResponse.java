package br.com.ciaf.admin.dtos;

import java.util.List;
import java.util.Map;

public class ImportPreviewResponse {
    private List<String> headers;
    private List<Map<String, String>> rows;

    public ImportPreviewResponse() {}

    public ImportPreviewResponse(List<String> headers, List<Map<String, String>> rows) {
        this.headers = headers;
        this.rows = rows;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, String>> rows) {
        this.rows = rows;
    }
}