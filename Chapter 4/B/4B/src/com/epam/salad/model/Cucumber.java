package com.epam.salad.model;

public class Cucumber extends Vegetable {
    private boolean isPickled;

    public Cucumber(String name, double weight, double caloriesPer100g, boolean isPickled) {
        super(name, weight, caloriesPer100g);
        this.isPickled = isPickled;
    }

    public boolean isPickled() {
        return isPickled;
    }

    public void setPickled(boolean pickled) {
        isPickled = pickled;
    }

    @Override
    public String getVegetableType() {
        return "Огурец (" + (isPickled ? "соленый" : "свежий") + ")";
    }
}