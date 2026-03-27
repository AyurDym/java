package com.epam.salad.model;

public class Carrot extends RootVegetable {
    public Carrot(String name, double weight, double caloriesPer100g, String rootType) {
        super(name, weight, caloriesPer100g, rootType);
    }

    @Override
    public String getVegetableType() {
        return "Морковь - " + super.getVegetableType();
    }
}