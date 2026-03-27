package com.epam.greenhouse.model;

public class BushPlant extends Plant {
    private double bushWidth; // ширина куста в метрах
    private boolean hasThorns; // наличие шипов
    private boolean fruits; // плодоносит

    public BushPlant(String name, String origin, double height, double bushWidth,
                     boolean hasThorns, boolean fruits) {
        super(name, origin, height);
        this.bushWidth = bushWidth;
        this.hasThorns = hasThorns;
        this.fruits = fruits;
    }

    public double getBushWidth() {
        return bushWidth;
    }

    public void setBushWidth(double bushWidth) {
        this.bushWidth = bushWidth;
    }

    public boolean isHasThorns() {
        return hasThorns;
    }

    public void setHasThorns(boolean hasThorns) {
        this.hasThorns = hasThorns;
    }

    public boolean isFruits() {
        return fruits;
    }

    public void setFruits(boolean fruits) {
        this.fruits = fruits;
    }

    public void prune() {
        System.out.println(name + " обрезан(а) для формирования кроны");
    }

    @Override
    public String getPlantType() {
        return "Кустовое растение";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", ширина куста: %.1fм, %s, %s",
                bushWidth,
                hasThorns ? "есть шипы" : "без шипов",
                fruits ? "плодоносит" : "не плодоносит");
    }
}
