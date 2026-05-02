package model;

import model.vegetable.Vegetable;
import java.io.Serializable;

public class SaladIngredient implements Serializable {
    private static final long serialVersionUID = 1L;

    private Vegetable vegetable;
    private double weightInGrams;  // вес в граммах

    public SaladIngredient(Vegetable vegetable, double weightInGrams) {
        this.vegetable = vegetable;
        this.weightInGrams = weightInGrams;
    }

    public Vegetable getVegetable() { return vegetable; }
    public void setVegetable(Vegetable vegetable) { this.vegetable = vegetable; }

    public double getWeightInGrams() { return weightInGrams; }
    public void setWeightInGrams(double weightInGrams) { this.weightInGrams = weightInGrams; }

    public double getCalories() {
        return vegetable.calculateCalories(weightInGrams);
    }

    @Override
    public String toString() {
        return String.format("%s (%.0fг, %.1f ккал)",
                vegetable.getName(), weightInGrams, getCalories());
    }
}