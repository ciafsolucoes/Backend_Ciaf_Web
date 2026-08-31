package br.com.ciaf.admin.util;

import java.text.Normalizer;
import java.util.Locale;

public class ColumnNormalizer {

    public static String normalize(String value) {
        if (value == null) return "";

        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]", "")
                .trim();
    }
}
