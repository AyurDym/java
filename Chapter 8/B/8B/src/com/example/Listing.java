package com.example;

public class Listing {
    private String code;
    private String language;

    public Listing(String code, String language) {
        this.code = normalizeCode(code);
        this.language = language;
    }

    private String normalizeCode(String code) {
        // Замена табуляций и последовательностей пробелов одним пробелом
        return code.replaceAll("\\t+", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "[Листинг на " + language + "]:\n" + code;
    }
}