package br.com.ciaf.admin.dtos;

public class ColumnMatchSuggestion {
    private String targetFieldKey;
    private String targetFieldLabel;
    private String sourceColumn;
    private double score;
    private boolean required;
    private String dataType;

    public ColumnMatchSuggestion() {}

    public ColumnMatchSuggestion(String targetFieldKey, String targetFieldLabel, String sourceColumn,
                                 double score, boolean required, String dataType) {
        this.targetFieldKey = targetFieldKey;
        this.targetFieldLabel = targetFieldLabel;
        this.sourceColumn = sourceColumn;
        this.score = score;
        this.required = required;
        this.dataType = dataType;
    }

    public String getTargetFieldKey() {
        return targetFieldKey;
    }

    public void setTargetFieldKey(String targetFieldKey) {
        this.targetFieldKey = targetFieldKey;
    }

    public String getTargetFieldLabel() {
        return targetFieldLabel;
    }

    public void setTargetFieldLabel(String targetFieldLabel) {
        this.targetFieldLabel = targetFieldLabel;
    }

    public String getSourceColumn() {
        return sourceColumn;
    }

    public void setSourceColumn(String sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
