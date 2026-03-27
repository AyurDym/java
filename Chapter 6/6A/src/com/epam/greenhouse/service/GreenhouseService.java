package com.epam.greenhouse.service;

import com.epam.greenhouse.model.*;

import java.util.List;

public class GreenhouseService {
    private Greenhouse greenhouse;

    public GreenhouseService(Greenhouse greenhouse) {
        this.greenhouse = greenhouse;
    }

    public boolean addPlant(Plant plant) {
        if (greenhouse.findPlantByName(plant.getName()) != null) {
            return false;
        }
        greenhouse.addPlant(plant);
        return true;
    }

    public boolean removePlant(String plantName) {
        return greenhouse.removePlant(plantName);
    }

    public Plant findPlantByName(String name) {
        return greenhouse.findPlantByName(name);
    }

    public void editPlantName(String oldName, String newName) {
        Plant plant = greenhouse.findPlantByName(oldName);
        if (plant != null) {
            plant.setName(newName);
        }
    }

    public void editPlantOrigin(String name, String newOrigin) {
        Plant plant = greenhouse.findPlantByName(name);
        if (plant != null) {
            plant.setOrigin(newOrigin);
        }
    }

    public void editPlantHeight(String name, double newHeight) {
        Plant plant = greenhouse.findPlantByName(name);
        if (plant != null) {
            plant.setHeight(newHeight);
        }
    }

    public void displayAllPlants() {
        List<Plant> plants = greenhouse.getPlants();
        if (plants.isEmpty()) {
            System.out.println("В оранжерее нет растений");
            return;
        }

        for (int i = 0; i < plants.size(); i++) {
            System.out.println((i + 1) + ". " + plants.get(i));
        }
        System.out.println("Всего растений: " + plants.size());
    }

    public void displayPlantsByType(String plantType) {
        List<Plant> plants = greenhouse.getPlantsByType(plantType);
        if (plants.isEmpty()) {
            System.out.println("Растения типа \"" + plantType + "\" не найдены");
            return;
        }

        for (Plant plant : plants) {
            System.out.println(plant);
        }
        System.out.println("Найдено: " + plants.size());
    }

    public void displayPlantsByOrigin(String origin) {
        List<Plant> plants = greenhouse.getPlantsByOrigin(origin);
        if (plants.isEmpty()) {
            System.out.println("Растения из \"" + origin + "\" не найдены");
            return;
        }

        for (Plant plant : plants) {
            System.out.println(plant);
        }
        System.out.println("Найдено: " + plants.size());
    }

    public void waterAllPlants() {
        System.out.println("\n--- Процесс полива ---");
        greenhouse.waterAllPlants();
        System.out.println("Полив завершен!");
    }

    public void setTemperature(double temperature) {
        greenhouse.setTemperature(temperature);
        if (!greenhouse.isTemperatureOptimal()) {
            System.out.println("Внимание! Температура не оптимальна для комнатных растений!");
        }
    }

    public void setLighting(String lightingLevel) {
        greenhouse.setLighting(lightingLevel);
        if (!greenhouse.isLightingSufficient()) {
            System.out.println("Внимание! Уровень освещения недостаточен!");
        }
    }

    public void displayGreenhouseInfo() {
        greenhouse.displayInfo();

        // Дополнительная информация по типам растений
        System.out.println("\n--- Статистика по типам растений ---");
        System.out.printf("Кустовые: %d\n", greenhouse.getPlantsByType("Кустовое растение").size());
        System.out.printf("Цветковые: %d\n", greenhouse.getPlantsByType("Цветковое растение").size());
        System.out.printf("Комнатные: %d\n", greenhouse.getPlantsByType("Комнатное растение").size());
    }
}
