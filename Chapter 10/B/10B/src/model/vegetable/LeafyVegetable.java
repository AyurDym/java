package model.vegetable;

public abstract class LeafyVegetable extends Vegetable {
    protected boolean isEdibleRaw;  // можно ли есть сырым

    public LeafyVegetable(String name, double caloriesPer100g, String color, boolean isEdibleRaw) {
        super(name, caloriesPer100g, color);
        this.isEdibleRaw = isEdibleRaw;
    }

    public boolean isEdibleRaw() { return isEdibleRaw; }
    public void setEdibleRaw(boolean edibleRaw) { isEdibleRaw = edibleRaw; }

    @Override
    public String getType() {
        return "Листовой овощ";
    }
}
