package service;

import model.Salad;
import model.SaladIngredient;
import java.util.Comparator;

public class VegetableSorter {

    public enum SortBy {
        NAME, CALORIES, WEIGHT, TYPE
    }

    public void sortByCalories(Salad salad, boolean ascending) {
        if (ascending) {
            salad.getIngredients().sort(Comparator.comparingDouble(SaladIngredient::getCalories));
        } else {
            salad.getIngredients().sort(Comparator.comparingDouble(SaladIngredient::getCalories).reversed());
        }
    }

    public void sortByName(Salad salad, boolean ascending) {
        if (ascending) {
            salad.getIngredients().sort(Comparator.comparing(i -> i.getVegetable().getName()));
        } else {
            salad.getIngredients().sort(Comparator.comparing(i -> i.getVegetable().getName(), Comparator.reverseOrder()));
        }
    }

    public void sortByWeight(Salad salad, boolean ascending) {
        if (ascending) {
            salad.getIngredients().sort(Comparator.comparingDouble(SaladIngredient::getWeightInGrams));
        } else {
            salad.getIngredients().sort(Comparator.comparingDouble(SaladIngredient::getWeightInGrams).reversed());
        }
    }

    public void sortByType(Salad salad) {
        salad.getIngredients().sort(Comparator.comparing(i -> i.getVegetable().getType()));
    }
}