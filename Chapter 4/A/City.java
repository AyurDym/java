import java.util.Objects;

public class City {
    private String name;
    private int population;
    private double area;
    private boolean isCapital;

    public City(String name, int population, double area, boolean isCapital) {
        this.name = name;
        this.population = population;
        this.area = area;
        this.isCapital = isCapital;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public double getArea() {
        return area;
    }

    public boolean isCapital() {
        return isCapital;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return population == city.population &&
                Double.compare(city.area, area) == 0 &&
                isCapital == city.isCapital &&
                Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, population, area, isCapital);
    }

    @Override
    public String toString() {
        return name + " (население: " + population + ", площадь: " + area + " км²" +
                (isCapital ? ", СТОЛИЦА" : "") + ")";
    }
}
