package com.epam.salad.model;

public class Potato extends RootVegetable {
    public Potato(String name, double weight, double caloriesPer100g, String rootType) {
        super(name, weight, caloriesPer100g, rootType);
    }

    @Override
    public String getVegetableType() {
        return "Картофель - " + super.getVegetableType();
    }
}