import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class District {
    private String name;
    private List<City> cities;

    public District(String name) {
        this.name = name;
        this.cities = new ArrayList<>();
    }

    public void addCity(City city) {
        cities.add(city);
    }

    public String getName() {
        return name;
    }

    public List<City> getCities() {
        return cities;
    }

    public double getTotalArea() {
        double total = 0;
        for (City city : cities) {
            total += city.getArea();
        }
        return total;
    }

    public int getTotalPopulation() {
        int total = 0;
        for (City city : cities) {
            total += city.getPopulation();
        }
        return total;
    }

    public City getCenter() {
        // Для простоты считаем центром района самый крупный город по населению
        if (cities.isEmpty()) return null;
        City center = cities.get(0);
        for (City city : cities) {
            if (city.getPopulation() > center.getPopulation()) {
                center = city;
            }
        }
        return center;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        District district = (District) o;
        return Objects.equals(name, district.name) &&
                Objects.equals(cities, district.cities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cities);
    }

    @Override
    public String toString() {
        return "Район: " + name + ", города: " + cities.size();
    }
}
