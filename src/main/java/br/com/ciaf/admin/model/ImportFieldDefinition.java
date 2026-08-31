package br.com.ciaf.admin.model;

public class ImportFieldDefinition {
    private final String key;
    private final String label;
    private final String dataType;
    private final boolean required;

    public ImportFieldDefinition(String key, String label, String dataType, boolean required) {
        this.key = key;
        this.label = label;
        this.dataType = dataType;
        this.required = required;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public String getDataType() {
        return dataType;
    }

    public boolean isRequired() {
        return required;
    }
}
