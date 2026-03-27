package com.epam.salad.model;

public abstract class Vegetable {
    private String name;
    private double weight; // вес в граммах
    private double caloriesPer100g; // калорийность на 100 грамм

    public Vegetable(String name, double weight, double caloriesPer100g) {
        this.name = name;
        this.weight = weight;
        this.caloriesPer100g = caloriesPer100g;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCaloriesPer100g() {
        return caloriesPer100g;
    }

    public void setCaloriesPer100g(double caloriesPer100g) {
        this.caloriesPer100g = caloriesPer100g;
    }

    public double calculateCalories() {
        return (caloriesPer100g * weight) / 100;
    }

    public abstract String getVegetableType();

    @Override
    public String toString() {
        return String.format("%s (тип: %s) - вес: %.0fг, калорийность: %.2f ккал/100г, всего: %.2f ккал",
                name, getVegetableType(), weight, caloriesPer100g, calculateCalories());
    }
}