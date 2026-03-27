package com.epam.greenhouse.model;

public class IndoorPlant extends Plant {
    private boolean needsSupport;
    private String lightRequirement;
    private double optimalTemperature;

    public IndoorPlant(String name, String origin, double height,
                       boolean needsSupport, String lightRequirement, double optimalTemperature) {
        super(name, origin, height);
        this.needsSupport = needsSupport;
        this.lightRequirement = lightRequirement;
        this.optimalTemperature = optimalTemperature;
    }

    public boolean isNeedsSupport() {
        return needsSupport;
    }

    public void setNeedsSupport(boolean needsSupport) {
        this.needsSupport = needsSupport;
    }

    public String getLightRequirement() {
        return lightRequirement;
    }

    public void setLightRequirement(String lightRequirement) {
        this.lightRequirement = lightRequirement;
    }

    public double getOptimalTemperature() {
        return optimalTemperature;
    }

    public void setOptimalTemperature(double optimalTemperature) {
        this.optimalTemperature = optimalTemperature;
    }

    public void provideSupport() {
        if (needsSupport) {
            System.out.println(name + " установлена опора для поддержки");
        }
    }

    @Override
    public String getPlantType() {
        return "Комнатное растение";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", требует опоры: %s, освещение: %s, оптим. темп.: %.1f°C",
                needsSupport ? "да" : "нет",
                lightRequirement,
                optimalTemperature);
    }
}
