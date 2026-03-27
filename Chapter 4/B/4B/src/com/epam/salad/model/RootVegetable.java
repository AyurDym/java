package com.epam.salad.model;

public class RootVegetable extends Vegetable {
    private String rootType; // тип корнеплода

    public RootVegetable(String name, double weight, double caloriesPer100g, String rootType) {
        super(name, weight, caloriesPer100g);
        this.rootType = rootType;
    }

    public String getRootType() {
        return rootType;
    }

    public void setRootType(String rootType) {
        this.rootType = rootType;
    }

    @Override
    public String getVegetableType() {
        return "Корнеплод (" + rootType + ")";
    }
}
