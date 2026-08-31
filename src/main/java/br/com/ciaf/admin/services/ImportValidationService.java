package br.com.ciaf.admin.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ImportValidationService {

    public List<String> validateRequiredFields(Map<String, String> row, List<String> requiredFields) {
        List<String> errors = new ArrayList<>();

        for (String field : requiredFields) {
            String value = row.get(field);
            if (value == null || value.isBlank()) {
                errors.add("Campo obrigatório ausente: " + field);
            }
        }

        return errors;
    }
}