package model.vegetable;

public class Lettuce extends LeafyVegetable {
    public Lettuce() {
        super("Салат", 15, "Светло-зеленый", true);
    }

    @Override
    public String getTasteDescription() {
        return "Нежный, сочный";
    }
}
