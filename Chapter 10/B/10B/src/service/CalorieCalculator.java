package service;

import model.Salad;
import model.SaladIngredient;

public class CalorieCalculator {

    public double calculateTotalCalories(Salad salad) {
        if (salad == null || salad.getIngredients().isEmpty()) {
            return 0;
        }
        return salad.getTotalCalories();
    }

    public double calculateAverageCaloriesPer100g(Salad salad) {
        if (salad == null || salad.getIngredients().isEmpty()) {
            return 0;
        }

        double totalWeight = salad.getIngredients().stream()
                .mapToDouble(SaladIngredient::getWeightInGrams)
                .sum();

        if (totalWeight == 0) return 0;

        return (salad.getTotalCalories() / totalWeight) * 100;
    }
}
