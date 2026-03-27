package com.epam.greenhouse.model;

import com.epam.greenhouse.interfaces.*;

import java.util.ArrayList;
import java.util.List;

public class Greenhouse implements TemperatureControllable, LightControllable {
    private String name;
    private double temperature;
    private String lightingLevel;
    private List<Plant> plants;

    public Greenhouse(String name, double temperature, int lightingLevel) {
        this.name = name;
        this.temperature = temperature;
        this.lightingLevel = lightingLevel + "%";
        this.plants = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public boolean removePlant(String plantName) {
        return plants.removeIf(plant -> plant.getName().equalsIgnoreCase(plantName));
    }

    public Plant findPlantByName(String name) {
        for (Plant plant : plants) {
            if (plant.getName().equalsIgnoreCase(name)) {
                return plant;
            }
        }
        return null;
    }

    public List<Plant> getPlantsByType(String plantType) {
        List<Plant> result = new ArrayList<>();
        for (Plant plant : plants) {
            if (plant.getPlantType().equals(plantType)) {
                result.add(plant);
            }
        }
        return result;
    }

    public List<Plant> getPlantsByOrigin(String origin) {
        List<Plant> result = new ArrayList<>();
        for (Plant plant : plants) {
            if (plant.getOrigin().equalsIgnoreCase(origin)) {
                result.add(plant);
            }
        }
        return result;
    }

    @Override
    public void setTemperature(double temperature) {
        this.temperature = temperature;
        System.out.println("Температура в оранжерее установлена на " + temperature + "°C");
    }

    @Override
    public double getCurrentTemperature() {
        return temperature;
    }

    @Override
    public boolean isTemperatureOptimal() {
        // Проверка для комнатных растений - для простоты считаем оптимальной 20-25°C
        return temperature >= 20 && temperature <= 25;
    }

    @Override
    public void setLighting(String lightingLevel) {
        this.lightingLevel = lightingLevel;
        System.out.println("Освещение установлено на уровень: " + lightingLevel);
    }

    @Override
    public String getCurrentLighting() {
        return lightingLevel;
    }

    @Override
    public boolean isLightingSufficient() {
        return lightingLevel != null &&
                (lightingLevel.equalsIgnoreCase("высокий") ||
                        lightingLevel.equalsIgnoreCase("умеренный"));
    }

    public void waterAllPlants() {
        for (Plant plant : plants) {
            if (plant.needsWatering()) {
                plant.water();
            } else {
                System.out.println(plant.getName() + " уже полит(а)");
            }
        }
    }

    public void resetWatering() {
        for (Plant plant : plants) {
            plant.isWatered = false;
        }
    }

    public void displayInfo() {
        System.out.println("\n=== Информация об оранжерее ===");
        System.out.println("Название: " + name);
        System.out.printf("Температура: %.1f°C (%s)\n", temperature,
                isTemperatureOptimal() ? "оптимальная" : "требует корректировки");
        System.out.println("Освещение: " + lightingLevel +
                " (" + (isLightingSufficient() ? "достаточное" : "недостаточное") + ")");
        System.out.println("Количество растений: " + plants.size());
    }
}