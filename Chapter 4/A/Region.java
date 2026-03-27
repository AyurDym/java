import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Region {
    private String name;
    private List<District> districts;

    public Region(String name) {
        this.name = name;
        this.districts = new ArrayList<>();
    }

    public void addDistrict(District district) {
        districts.add(district);
    }

    public String getName() {
        return name;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public double getTotalArea() {
        double total = 0;
        for (District district : districts) {
            total += district.getTotalArea();
        }
        return total;
    }

    public int getTotalPopulation() {
        int total = 0;
        for (District district : districts) {
            total += district.getTotalPopulation();
        }
        return total;
    }

    public City getCenter() {
        // Областной центр - самый крупный город области
        City center = null;
        int maxPopulation = -1;
        for (District district : districts) {
            for (City city : district.getCities()) {
                if (city.getPopulation() > maxPopulation) {
                    maxPopulation = city.getPopulation();
                    center = city;
                }
            }
        }
        return center;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(name, region.name) &&
                Objects.equals(districts, region.districts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, districts);
    }

    @Override
    public String toString() {
        return "Область: " + name + ", районы: " + districts.size();
    }
}
