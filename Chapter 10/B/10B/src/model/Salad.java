package model;

import model.vegetable.Vegetable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Salad implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private List<SaladIngredient> ingredients;

    private static String defaultDressing = "Оливковое масло";
    private transient String preparationDate;

    public Salad(String name) {
        this.name = name;
        this.ingredients = new ArrayList<>();
        this.preparationDate = new java.util.Date().toString();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<SaladIngredient> getIngredients() { return ingredients; }

    public void addIngredient(SaladIngredient ingredient) {
        ingredients.add(ingredient);
    }

    public boolean removeIngredient(Vegetable vegetable) {
        return ingredients.removeIf(i -> i.getVegetable().equals(vegetable));
    }

    public double getTotalCalories() {
        return ingredients.stream()
                .mapToDouble(SaladIngredient::getCalories)
                .sum();
    }

    public static String getDefaultDressing() { return defaultDressing; }
    public static void setDefaultDressing(String dressing) { defaultDressing = dressing; }

    public String getPreparationDate() { return preparationDate; }
    public void setPreparationDate(String date) { preparationDate = date; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Салат: ").append(name).append("\n");
        sb.append("Ингредиенты:\n");
        for (SaladIngredient ing : ingredients) {
            sb.append("  - ").append(ing).append("\n");
        }
        sb.append("Общая калорийность: ").append(String.format("%.1f", getTotalCalories())).append(" ккал\n");
        return sb.toString();
    }
}