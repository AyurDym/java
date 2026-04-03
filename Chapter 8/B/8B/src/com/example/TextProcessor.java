package com.example;

import java.util.*;

public class TextProcessor {
    private List<Paragraph> paragraphs;
    private List<Listing> listings;

    public TextProcessor() {
        this.paragraphs = new ArrayList<>();
        this.listings = new ArrayList<>();
    }

    public void parseText(String text) {
        // Нормализация текста: замена табуляций и множественных пробелов
        String normalizedText = text.replaceAll("\\t+", " ")
                .replaceAll("\\s+", " ")
                .trim();

        // Разделение на листинги и текст (упрощённо)
        String[] parts = normalizedText.split("\\[listing\\]|\\[\\/listing\\]");

        for (String part : parts) {
            if (part.trim().isEmpty()) continue;

            // Проверка, является ли часть листингом (упрощённо)
            if (part.contains("public class") || part.contains("import")) {
                listings.add(new Listing(part, "Java"));
            } else {
                parseParagraphs(part);
            }
        }
    }

    private void parseParagraphs(String text) {
        String[] paragraphStrings = text.split("\\n\\s*\\n");

        for (String paraStr : paragraphStrings) {
            if (paraStr.trim().isEmpty()) continue;
            paragraphs.add(parseParagraph(paraStr));
        }
    }

    private Paragraph parseParagraph(String paraStr) {
        Paragraph paragraph = new Paragraph();
        String[] sentenceStrings = paraStr.split("(?<=[.!?])\\s+");

        for (String sentStr : sentenceStrings) {
            if (sentStr.trim().isEmpty()) continue;
            paragraph.addSentence(parseSentence(sentStr));
        }

        return paragraph;
    }

    private Sentence parseSentence(String sentStr) {
        Sentence sentence = new Sentence();
        String[] lexemeStrings = sentStr.split("\\s+");

        for (String lexStr : lexemeStrings) {
            Lexeme lexeme = new Lexeme();
            StringBuilder currentWord = new StringBuilder();

            for (int i = 0; i < lexStr.length(); i++) {
                char c = lexStr.charAt(i);

                if (Character.isLetterOrDigit(c)) {
                    currentWord.append(c);
                } else {
                    if (currentWord.length() > 0) {
                        lexeme.addPart(new Word(currentWord.toString()));
                        currentWord = new StringBuilder();
                    }
                    lexeme.addPart(new Punctuation(c));
                }
            }

            if (currentWord.length() > 0) {
                lexeme.addPart(new Word(currentWord.toString()));
            }

            sentence.addLexeme(lexeme);
        }

        // Определение знака препинания в конце предложения
        if (sentStr.length() > 0) {
            char lastChar = sentStr.charAt(sentStr.length() - 1);
            if (lastChar == '.' || lastChar == '!' || lastChar == '?') {
                sentence.setEndingPunctuation(new Punctuation(lastChar));
            }
        }

        return sentence;
    }

    public void printWordsInInterrogativeSentences(int wordLength) {
        System.out.println("=" .repeat(60));
        System.out.println("Вопросительные предложения:");
        System.out.println("=" .repeat(60));

        Set<Word> allUniqueWords = new HashSet<>();

        for (Paragraph paragraph : paragraphs) {
            for (Sentence sentence : paragraph.getInterrogativeSentences()) {
                System.out.println("\n? " + sentence);
                Set<Word> words = sentence.getUniqueWordsOfLength(wordLength);
                allUniqueWords.addAll(words);
            }
        }

        System.out.println("\n" + "=" .repeat(60));
        System.out.println("Слова длины " + wordLength + " из вопросительных предложений (без повторений):");
        System.out.println("=" .repeat(60));

        if (allUniqueWords.isEmpty()) {
            System.out.println("Слова заданной длины не найдены.");
        } else {
            for (Word word : allUniqueWords) {
                System.out.println("• " + word);
            }
        }

        System.out.println("\nВсего уникальных слов: " + allUniqueWords.size());
    }

    public void printAllText() {
        System.out.println("=" .repeat(60));
        System.out.println("Обработанный текст:");
        System.out.println("=" .repeat(60));

        for (Paragraph paragraph : paragraphs) {
            System.out.println(paragraph);
            System.out.println();
        }

        for (Listing listing : listings) {
            System.out.println(listing);
            System.out.println();
        }
    }
}