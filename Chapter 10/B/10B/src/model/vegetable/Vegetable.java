package model.vegetable;

import java.io.Serializable;

public abstract class Vegetable implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;
    protected double caloriesPer100g;  // калорийность на 100 грамм
    protected String color;

    // static поле - общее для всех овощей
    protected static String defaultUnit = "грамм";

    // transient поле - не будет сериализовано
    protected transient String temporaryNote;

    public Vegetable(String name, double caloriesPer100g, String color) {
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.color = color;
        this.temporaryNote = "Временная метка: " + System.currentTimeMillis();
    }

    // Абстрактные методы
    public abstract String getType();
    public abstract String getTasteDescription();

    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getCaloriesPer100g() { return caloriesPer100g; }
    public void setCaloriesPer100g(double caloriesPer100g) { this.caloriesPer100g = caloriesPer100g; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public static String getDefaultUnit() { return defaultUnit; }
    public static void setDefaultUnit(String unit) { defaultUnit = unit; }

    public String getTemporaryNote() { return temporaryNote; }
    public void setTemporaryNote(String note) { this.temporaryNote = note; }

    // Расчет калорийности для заданного веса
    public double calculateCalories(double weightInGrams) {
        return (caloriesPer100g * weightInGrams) / 100;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %s, %.1f ккал/100г)",
                name, getType(), color, caloriesPer100g);
    }
}
