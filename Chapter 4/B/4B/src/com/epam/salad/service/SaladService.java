package com.epam.salad.service;

import com.epam.salad.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SaladService {
    private List<Vegetable> salad;

    public SaladService() {
        this.salad = new ArrayList<>();
    }

    public void addVegetableToSalad(Vegetable vegetable) {
        salad.add(vegetable);
    }

    public void addVegetableToSalad(String name, double weight, double caloriesPer100g) {
        Vegetable vegetable = new Vegetable(name, weight, caloriesPer100g) {
            @Override
            public String getVegetableType() {
                return "Овощ общего типа";
            }
        };
        salad.add(vegetable);
    }

    public void addDefaultVegetables() {
        salad.add(new Tomato("Помидор черри", 150, 18, "красный"));
        salad.add(new Cucumber("Огурец свежий", 200, 15, false));
        salad.add(new Carrot("Морковь молодая", 100, 41, "оранжевый корнеплод"));
        salad.add(new Potato("Картофель", 180, 77, "клубень"));
        salad.add(new Tomato("Помидор желтый", 120, 16, "желтый"));
        salad.add(new Cucumber("Огурец соленый", 150, 11, true));
    }

    public double calculateTotalCalories() {
        return salad.stream()
                .mapToDouble(Vegetable::calculateCalories)
                .sum();
    }

    public void sortByCalories() {
        salad.sort(Comparator.comparingDouble(Vegetable::getCaloriesPer100g));
    }

    public void sortByWeight() {
        salad.sort(Comparator.comparingDouble(Vegetable::getWeight));
    }

    public void sortByName() {
        salad.sort(Comparator.comparing(Vegetable::getName));
    }

    public List<Vegetable> findVegetablesByCalorieRange(double minCalories, double maxCalories) {
        List<Vegetable> result = new ArrayList<>();
        for (Vegetable vegetable : salad) {
            double calories = vegetable.getCaloriesPer100g();
            if (calories >= minCalories && calories <= maxCalories) {
                result.add(vegetable);
            }
        }
        return result;
    }

    public void displaySalad() {
        if (salad.isEmpty()) {
            System.out.println("Салат пуст. Добавьте овощи!");
            return;
        }

        for (int i = 0; i < salad.size(); i++) {
            System.out.println((i + 1) + ". " + salad.get(i));
        }
        System.out.println("Всего ингредиентов: " + salad.size());
    }

    public List<Vegetable> getSalad() {
        return salad;
    }
}