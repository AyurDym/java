package model.vegetable;

public class Potato extends RootVegetable {
    public Potato() {
        super("Картофель", 77, "Коричневый", 150);
    }

    @Override
    public String getTasteDescription() {
        return "Нейтральный, крахмалистый";
    }
}
