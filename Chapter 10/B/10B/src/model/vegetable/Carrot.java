package model.vegetable;

public class Carrot extends RootVegetable {
    public Carrot() {
        super("Морковь", 41, "Оранжевый", 75);
    }

    @Override
    public String getTasteDescription() {
        return "Сладковатый, сочный";
    }
}
