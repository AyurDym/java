package service;

import model.Salad;
import model.SaladIngredient;
import model.vegetable.Vegetable;

public class SaladService {
    private CalorieCalculator calorieCalculator = new CalorieCalculator();
    private VegetableSorter sorter = new VegetableSorter();
    private VegetableFilter filter = new VegetableFilter();

    public void addVegetable(Salad salad, Vegetable vegetable, double weight) {
        salad.addIngredient(new SaladIngredient(vegetable, weight));
    }

    public double getTotalCalories(Salad salad) {
        return calorieCalculator.calculateTotalCalories(salad);
    }

    public void sortByCalories(Salad salad, boolean ascending) {
        sorter.sortByCalories(salad, ascending);
    }

    public void sortByName(Salad salad, boolean ascending) {
        sorter.sortByName(salad, ascending);
    }

    public void sortByWeight(Salad salad, boolean ascending) {
        sorter.sortByWeight(salad, ascending);
    }

    public java.util.List<SaladIngredient> findVegetablesByCalorieRange(Salad salad, double min, double max) {
        return filter.findByCalorieRange(salad, min, max);
    }

    public void printSaladInfo(Salad salad) {
        System.out.println(salad);
        System.out.println("Средняя калорийность на 100г: " +
                String.format("%.1f", calorieCalculator.calculateAverageCaloriesPer100g(salad)) + " ккал");
    }
}
