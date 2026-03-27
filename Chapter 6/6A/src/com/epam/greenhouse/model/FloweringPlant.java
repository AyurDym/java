package com.epam.greenhouse.model;

public class FloweringPlant extends Plant {
    private String flowerColor;
    private String floweringPeriod;

    public FloweringPlant(String name, String origin, double height,
                          String flowerColor, String floweringPeriod) {
        super(name, origin, height);
        this.flowerColor = flowerColor;
        this.floweringPeriod = floweringPeriod;
    }

    public String getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(String flowerColor) {
        this.flowerColor = flowerColor;
    }

    public String getFloweringPeriod() {
        return floweringPeriod;
    }

    public void setFloweringPeriod(String floweringPeriod) {
        this.floweringPeriod = floweringPeriod;
    }

    public void bloom() {
        System.out.println(name + " зацвел(а)! Цветы " + flowerColor + " цвета");
    }

    @Override
    public String getPlantType() {
        return "Цветковое растение";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", цвет: %s, период цветения: %s",
                flowerColor, floweringPeriod);
    }
}
