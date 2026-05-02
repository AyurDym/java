package model.vegetable;

public class Cabbage extends LeafyVegetable {
    public Cabbage() {
        super("Капуста", 25, "Зеленый", true);
    }

    @Override
    public String getTasteDescription() {
        return "Свежий, хрустящий";
    }
}
