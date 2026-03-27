package com.epam.salad.model;

public class Tomato extends Vegetable {
    private String color;

    public Tomato(String name, double weight, double caloriesPer100g, String color) {
        super(name, weight, caloriesPer100g);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getVegetableType() {
        return "Помидор (" + color + ")";
    }
}