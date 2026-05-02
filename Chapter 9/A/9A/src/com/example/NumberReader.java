package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NumberReader {

    private List<Double> numbers;

    public NumberReader() {
        numbers = new ArrayList<>();
    }

    public void readFromFile(String filePath) throws InvalidNumberException, IOException {
        numbers.clear();

        File file = new File(filePath);

        // Проверка существования файла
        if (!file.exists()) {
            throw new FileNotFoundException("Файл не найден по адресу: " + filePath);
        }

        // Проверка, что это файл, а не директория
        if (file.isDirectory()) {
            throw new IOException("Указанный путь ведёт к директории, а не к файлу: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                // Пропускаем пустые строки
                if (line.isEmpty()) {
                    continue;
                }

                // Ожидаем формат: число|локаль или число локаль
                String[] parts = line.split("[|\\s]+");

                if (parts.length < 2) {
                    throw new InvalidNumberException(
                            String.format("Строка %d имеет некорректный формат. Ожидается: число|локаль", lineNumber)
                    );
                }

                String numberStr = parts[0].trim();
                String localeStr = parts[1].trim();

                // Проверка локали
                Locale locale;
                if (localeStr.equalsIgnoreCase("RU") || localeStr.equalsIgnoreCase("ru")) {
                    locale = new Locale("ru", "RU");
                } else if (localeStr.equalsIgnoreCase("US") || localeStr.equalsIgnoreCase("us") ||
                        localeStr.equalsIgnoreCase("EN") || localeStr.equalsIgnoreCase("en")) {
                    locale = Locale.US;
                } else {
                    throw new InvalidNumberException(
                            String.format("Строка %d: неподдерживаемая локаль '%s'. Используйте RU или US", lineNumber, localeStr)
                    );
                }

                // Парсинг числа с учётом локали
                double value;
                try {
                    // Для русской локали заменяем запятую на точку для парсинга
                    if (locale.getLanguage().equals("ru")) {
                        numberStr = numberStr.replace(',', '.');
                        value = Double.parseDouble(numberStr);
                    } else {
                        value = Double.parseDouble(numberStr);
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidNumberException(
                            String.format("Строка %d: '%s' не является корректным числом для локали %s",
                                    lineNumber, numberStr, localeStr), e
                    );
                }

                // Проверка на выход за пределы допустимых значений
                if (Double.isInfinite(value)) {
                    throw new InvalidNumberException(
                            String.format("Строка %d: число %s выходит за пределы допустимых значений (бесконечность)",
                                    lineNumber, numberStr)
                    );
                }

                if (Double.isNaN(value)) {
                    throw new InvalidNumberException(
                            String.format("Строка %d: число %s является NaN (не число)", lineNumber, numberStr)
                    );
                }

                // Дополнительная проверка на минимальное и максимальное значение
                if (Math.abs(value) > 1e308) {
                    throw new InvalidNumberException(
                            String.format("Строка %d: число %s выходит за пределы допустимых значений (> 1e308)",
                                    lineNumber, numberStr)
                    );
                }

                numbers.add(value);
            }
        } catch (OutOfMemoryError e) {
            throw new InvalidNumberException("Недостаточно памяти для чтения файла", e);
        }

        if (numbers.isEmpty()) {
            throw new InvalidNumberException("Файл не содержит корректных чисел");
        }
    }

    public NumberStats calculateStats() throws InvalidNumberException {
        if (numbers.isEmpty()) {
            throw new InvalidNumberException("Нет данных для вычисления статистики");
        }

        double sum = 0.0;
        for (double num : numbers) {
            sum += num;
        }
        double average = sum / numbers.size();

        return new NumberStats(sum, average, numbers.size());
    }

    public List<Double> getNumbers() {
        return new ArrayList<>(numbers);
    }
}