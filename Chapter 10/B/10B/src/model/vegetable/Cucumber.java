package model.vegetable;

public class Cucumber extends FruitVegetable {
    public Cucumber() {
        super("Огурец", 15, "Зеленый", 96);
    }

    @Override
    public String getTasteDescription() {
        return "Освежающий, хрустящий";
    }
}
