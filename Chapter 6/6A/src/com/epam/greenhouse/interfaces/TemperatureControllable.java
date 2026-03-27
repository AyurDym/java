package com.epam.greenhouse.interfaces;

public interface TemperatureControllable {
    void setTemperature(double temperature);
    double getCurrentTemperature();
    boolean isTemperatureOptimal();
}
