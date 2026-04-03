package com.example;

public class Punctuation {
    private char sign;
    private String type;

    public Punctuation(char sign) {
        this.sign = sign;
        this.type = determineType(sign);
    }

    private String determineType(char sign) {
        switch (sign) {
            case '.': return "PERIOD";
            case ',': return "COMMA";
            case '!': return "EXCLAMATION";
            case '?': return "QUESTION";
            case ';': return "SEMICOLON";
            case ':': return "COLON";
            default: return "OTHER";
        }
    }

    public boolean isQuestionMark() {
        return sign == '?';
    }

    public char getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return String.valueOf(sign);
    }
}