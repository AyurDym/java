package service;

import model.FrequencyInfo;
import java.util.*;
import java.util.stream.Collectors;

public class AnalysisService {

    // Задание 8: Анализ частоты букв и слов
    public Map<Character, Integer> analyzeLetterFrequency(String text) {
        Map<Character, Integer> frequency = new HashMap<>();
        String lettersOnly = text.toLowerCase().replaceAll("[^\\p{L}]", "");

        for (char c : lettersOnly.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }

        return frequency.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Integer> analyzeWordFrequency(String text) {
        Map<String, Integer> frequency = new HashMap<>();
        String cleanText = text.toLowerCase().replaceAll("[^\\p{L}\\p{N}\\s]", " ");
        String[] words = cleanText.split("\\s+");

        for (String word : words) {
            if (word.length() > 0) {
                frequency.put(word, frequency.getOrDefault(word, 0) + 1);
            }
        }

        return frequency.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    // Сортировка частотного словаря по возрастанию
    public List<FrequencyInfo> sortByFrequencyAscending(Map<String, Integer> frequencyMap) {
        List<FrequencyInfo> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            list.add(new FrequencyInfo(entry.getKey(), entry.getValue()));
        }
        Collections.sort(list);
        return list;
    }
}