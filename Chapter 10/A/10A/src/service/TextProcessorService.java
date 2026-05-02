package service;

import model.WordInfo;
import java.util.*;
import java.util.regex.*;

public class TextProcessorService {

    private static final Set<Character> VOWELS = Set.of(
            'а', 'е', 'ё', 'и', 'о', 'у', 'ы', 'э', 'ю', 'я',
            'a', 'e', 'i', 'o', 'u', 'y'
    );

    // Задание 1: Удаление подстроки
    public List<String> removeSubstring(List<String> lines, String substring) {
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            result.add(line.replace(substring, ""));
        }
        return result;
    }

    // Задание 2: Замена подстроки
    public List<String> replaceSubstring(List<String> lines, String oldSub, String newSub) {
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            result.add(line.replace(oldSub, newSub));
        }
        return result;
    }

    // Задание 3: Поиск слов на гласную
    public List<String> findWordsStartingWithVowel(List<String> lines) {
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            String[] words = line.split("[\\s,;:!?.()\\[\\]\"]+");
            List<String> vowelWords = new ArrayList<>();

            for (String word : words) {
                if (word.length() > 0) {
                    char firstChar = Character.toLowerCase(word.charAt(0));
                    if (VOWELS.contains(firstChar)) {
                        vowelWords.add(word);
                    }
                }
            }

            if (!vowelWords.isEmpty()) {
                result.add(line + " -> " + String.join(", ", vowelWords));
            }
        }
        return result;
    }

    // Задание 4: Поиск цепочек слов
    public List<String[]> findWordChains(List<String> lines) {
        List<String[]> chains = new ArrayList<>();

        for (String line : lines) {
            String[] words = line.split("[\\s,;:!?.()\\[\\]\"]+");

            for (int i = 0; i < words.length - 1; i++) {
                if (words[i].length() > 0 && words[i + 1].length() > 0) {
                    char lastChar = Character.toLowerCase(words[i].charAt(words[i].length() - 1));
                    char firstChar = Character.toLowerCase(words[i + 1].charAt(0));

                    if (lastChar == firstChar) {
                        chains.add(new String[]{words[i], words[i + 1]});
                    }
                }
            }
        }
        return chains;
    }

    // Задание 5: Поиск максимальной последовательности цифр
    public Map<String, Integer> findMaxDigitSequence(List<String> lines) {
        Map<String, Integer> result = new HashMap<>();
        Pattern digitPattern = Pattern.compile("\\d+");

        for (String line : lines) {
            Matcher matcher = digitPattern.matcher(line);
            int maxLength = 0;
            String longestSequence = "";

            while (matcher.find()) {
                String sequence = matcher.group();
                if (sequence.length() > maxLength) {
                    maxLength = sequence.length();
                    longestSequence = sequence;
                }
            }

            if (maxLength > 0) {
                result.put(line, maxLength);
            }
        }
        return result;
    }

    // Задание 6: Подсчет частоты слов из списка
    public Map<String, Integer> countWordFrequency(String text, List<String> targetWords) {
        Map<String, Integer> frequency = new HashMap<>();
        for (String word : targetWords) {
            frequency.put(word.toLowerCase(), 0);
        }

        String cleanText = text.toLowerCase().replaceAll("[^\\p{L}\\s]", " ");
        String[] words = cleanText.split("\\s+");

        for (String word : words) {
            String lowerWord = word.toLowerCase();
            if (frequency.containsKey(lowerWord)) {
                frequency.put(lowerWord, frequency.get(lowerWord) + 1);
            }
        }

        return frequency;
    }

    // Задание 7: Замена первой буквы на прописную
    public List<String> capitalizeFirstLetter(List<String> lines) {
        List<String> result = new ArrayList<>();

        for (String line : lines) {
            String[] words = line.split("(?=[\\s])|\\b");
            StringBuilder modifiedLine = new StringBuilder();

            for (String word : words) {
                if (word.length() > 0 && Character.isLetter(word.charAt(0))) {
                    modifiedLine.append(Character.toUpperCase(word.charAt(0)));
                    if (word.length() > 1) {
                        modifiedLine.append(word.substring(1));
                    }
                } else {
                    modifiedLine.append(word);
                }
            }
            result.add(modifiedLine.toString());
        }
        return result;
    }

    // Разбиение строки на слова
    public List<WordInfo> splitToWords(String line) {
        List<WordInfo> words = new ArrayList<>();
        String[] rawWords = line.split("[\\s,;:!?.()\\[\\]\"]+");
        int position = 0;

        for (String rawWord : rawWords) {
            if (rawWord.length() > 0) {
                words.add(new WordInfo(rawWord, position++));
            }
        }
        return words;
    }
}