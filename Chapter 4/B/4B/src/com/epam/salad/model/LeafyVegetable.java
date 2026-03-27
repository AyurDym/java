package com.epam.salad.model;

public class LeafyVegetable extends Vegetable {
    private boolean isEdibleRaw; // можно ли есть сырым

    public LeafyVegetable(String name, double weight, double caloriesPer100g, boolean isEdibleRaw) {
        super(name, weight, caloriesPer100g);
        this.isEdibleRaw = isEdibleRaw;
    }

    public boolean isEdibleRaw() {
        return isEdibleRaw;
    }

    public void setEdibleRaw(boolean edibleRaw) {
        isEdibleRaw = edibleRaw;
    }

    @Override
    public String getVegetableType() {
        return "Листовой овощ (" + (isEdibleRaw ? "можно есть сырым" : "требует термической обработки") + ")";
    }
}
