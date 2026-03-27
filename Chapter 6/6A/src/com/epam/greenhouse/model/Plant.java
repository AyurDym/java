package com.epam.greenhouse.model;

import com.epam.greenhouse.interfaces.*;

public abstract class Plant implements Waterable {
    protected String name;
    protected String origin;
    protected double height; // высота в метрах
    protected boolean isWatered;

    public Plant(String name, String origin, double height) {
        this.name = name;
        this.origin = origin;
        this.height = height;
        this.isWatered = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isWatered() {
        return isWatered;
    }

    @Override
    public void water() {
        this.isWatered = true;
        System.out.println(name + " полит(а)");
    }

    @Override
    public boolean needsWatering() {
        return !isWatered;
    }

    public abstract String getPlantType();

    @Override
    public String toString() {
        return String.format("%s: %s (происхождение: %s, высота: %.1fм, %s)",
                getPlantType(), name, origin, height,
                isWatered ? "полито" : "требует полива");
    }
}
