package model.vegetable;

public abstract class FruitVegetable extends Vegetable {
    protected double waterContent;  // содержание воды в процентах

    public FruitVegetable(String name, double caloriesPer100g, String color, double waterContent) {
        super(name, caloriesPer100g, color);
        this.waterContent = waterContent;
    }

    public double getWaterContent() { return waterContent; }
    public void setWaterContent(double waterContent) { this.waterContent = waterContent; }

    @Override
    public String getType() {
        return "Плодовый овощ";
    }
}