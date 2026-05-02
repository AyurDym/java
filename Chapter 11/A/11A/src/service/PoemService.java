package service;

import model.Poem;
import model.Line;
import model.Author;
import exception.PoemException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PoemService {

    private LineSorter lineSorter;

    public PoemService() {
        this.lineSorter = new LineSorter();
    }

    // Загрузка стихотворений из текстового файла
    public Author loadPoemsFromFile(String filePath) throws PoemException {
        Author author = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentTitle = null;
            Poem currentPoem = null;
            String authorName = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) continue;

                // Если строка начинается с "---" - это разделитель стихотворений
                if (line.startsWith("---")) {
                    currentPoem = null;
                    continue;
                }

                // Если строка начинается с "Автор:" - определяем автора
                if (line.startsWith("Автор:")) {
                    authorName = line.substring(6).trim();
                    author = new Author(authorName);
                    continue;
                }

                // Если строка начинается с "Название:" - новое стихотворение
                if (line.startsWith("Название:")) {
                    String title = line.substring(9).trim();
                    currentPoem = new Poem(title, authorName);
                    if (author != null) {
                        author.addPoem(currentPoem);
                    }
                    currentTitle = title;
                    continue;
                }

                // Если строка начинается с "Год:" - добавляем год
                if (line.startsWith("Год:") && currentPoem != null) {
                    try {
                        int year = Integer.parseInt(line.substring(4).trim());
                        currentPoem.setYear(year);
                    } catch (NumberFormatException e) {
                        // Игнорируем ошибку парсинга года
                    }
                    continue;
                }

                // Иначе - это строка стихотворения
                if (currentPoem != null && !line.isEmpty()) {
                    currentPoem.addLine(line);
                }
            }

        } catch (FileNotFoundException e) {
            throw new PoemException("Файл не найден: " + filePath, e);
        } catch (IOException e) {
            throw new PoemException("Ошибка чтения файла: " + filePath, e);
        }

        if (author == null) {
            throw new PoemException("Не удалось загрузить стихотворения из файла");
        }

        return author;
    }

    // Ручное добавление стихотворения
    public Poem createPoem(String title, String author, List<String> lines) {
        Poem poem = new Poem(title, author);
        for (String line : lines) {
            poem.addLine(line);
        }
        return poem;
    }

    // Сортировка строк в стихотворении (возвращает новый список)
    public List<Line> getSortedLines(Poem poem) {
        return lineSorter.sortLinesByLengthAscending(poem);
    }

    // Вывод информации о стихотворении с отсортированными строками
    public void displayPoemWithSortedLines(Poem poem) {
        System.out.println("\n=== " + poem.getTitle() + " ===");
        System.out.println("Автор: " + poem.getAuthor());
        System.out.println("\nОригинальные строки:");
        for (Line line : poem.getLines()) {
            System.out.println("  " + line.getText());
        }

        lineSorter.printSortedLines(poem, true);
    }

    // Сохранение стихотворений в файл
    public void savePoemsToFile(Author author, String filePath) throws PoemException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Автор: " + author.getName());
            writer.newLine();
            writer.newLine();

            for (Poem poem : author.getPoems()) {
                writer.write("Название: " + poem.getTitle());
                writer.newLine();

                if (poem.getYear() > 0) {
                    writer.write("Год: " + poem.getYear());
                    writer.newLine();
                }

                writer.newLine();

                for (Line line : poem.getLines()) {
                    writer.write(line.getText());
                    writer.newLine();
                }

                writer.write("---");
                writer.newLine();
                writer.newLine();
            }

        } catch (IOException e) {
            throw new PoemException("Ошибка сохранения файла: " + filePath, e);
        }
    }
}
