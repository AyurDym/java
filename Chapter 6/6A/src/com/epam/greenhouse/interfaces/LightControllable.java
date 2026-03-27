package com.epam.greenhouse.interfaces;

public interface LightControllable {
    void setLighting(String lightingLevel);
    String getCurrentLighting();
    boolean isLightingSufficient();
}