package com.epam.salad.util;

import com.epam.salad.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<Vegetable> loadVegetablesFromFile(String filename) {
        List<Vegetable> vegetables = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Vegetable vegetable = parseVegetable(line);
                if (vegetable != null) {
                    vegetables.add(vegetable);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filename);
            createDefaultFile(filename);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return vegetables;
    }

    private static Vegetable parseVegetable(String line) {
        String[] parts = line.split(",");
        if (parts.length < 4) {
            return null;
        }

        String type = parts[0].trim();
        String name = parts[1].trim();
        double weight = Double.parseDouble(parts[2].trim());
        double calories = Double.parseDouble(parts[3].trim());

        switch (type.toLowerCase()) {
            case "tomato":
                String color = parts.length > 4 ? parts[4].trim() : "красный";
                return new Tomato(name, weight, calories, color);
            case "cucumber":
                boolean isPickled = parts.length > 4 && parts[4].trim().equalsIgnoreCase("true");
                return new Cucumber(name, weight, calories, isPickled);
            case "carrot":
                String rootType = parts.length > 4 ? parts[4].trim() : "корнеплод";
                return new Carrot(name, weight, calories, rootType);
            case "potato":
                rootType = parts.length > 4 ? parts[4].trim() : "клубень";
                return new Potato(name, weight, calories, rootType);
            default:
                return null;
        }
    }

    private static void createDefaultFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("tomato,Помидор черри,150,18,красный\n");
            writer.write("cucumber,Огурец свежий,200,15,false\n");
            writer.write("carrot,Морковь молодая,100,41,оранжевый корнеплод\n");
            writer.write("potato,Картофель,180,77,клубень\n");
            writer.write("tomato,Помидор желтый,120,16,желтый\n");
            writer.write("cucumber,Огурец соленый,150,11,true\n");
            System.out.println("Создан файл с овощами по умолчанию: " + filename);
        } catch (IOException e) {
            System.out.println("Не удалось создать файл по умолчанию: " + e.getMessage());
        }
    }
}
