package model.vegetable;

public class Tomato extends FruitVegetable {
    public Tomato() {
        super("Помидор", 18, "Красный", 94);
    }

    @Override
    public String getTasteDescription() {
        return "Кисло-сладкий";
    }
}
