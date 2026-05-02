package model.vegetable;

public abstract class RootVegetable extends Vegetable {
    protected double averageWeight;  // средний вес в граммах

    public RootVegetable(String name, double caloriesPer100g, String color, double averageWeight) {
        super(name, caloriesPer100g, color);
        this.averageWeight = averageWeight;
    }

    public double getAverageWeight() { return averageWeight; }
    public void setAverageWeight(double averageWeight) { this.averageWeight = averageWeight; }

    @Override
    public String getType() {
        return "Корнеплод";
    }
}
