package service;

import model.Salad;
import model.SaladIngredient;
import java.util.List;
import java.util.stream.Collectors;

public class VegetableFilter {

    public List<SaladIngredient> findByCalorieRange(Salad salad, double minCalories, double maxCalories) {
        return salad.getIngredients().stream()
                .filter(ingredient -> {
                    double calories = ingredient.getCalories();
                    return calories >= minCalories && calories <= maxCalories;
                })
                .collect(Collectors.toList());
    }

    public List<SaladIngredient> findByVegetableType(Salad salad, String type) {
        return salad.getIngredients().stream()
                .filter(ingredient -> ingredient.getVegetable().getType().equals(type))
                .collect(Collectors.toList());
    }

    public List<SaladIngredient> findByColor(Salad salad, String color) {
        return salad.getIngredients().stream()
                .filter(ingredient -> ingredient.getVegetable().getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }
}
