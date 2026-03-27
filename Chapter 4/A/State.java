import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class State {
    private String name;
    private List<Region> regions;
    private City capital;

    public State(String name) {
        this.name = name;
        this.regions = new ArrayList<>();
        this.capital = null;
    }

    public void addRegion(Region region) {
        regions.add(region);
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public String getName() {
        return name;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public City getCapital() {
        return capital;
    }

    public int getRegionsCount() {
        return regions.size();
    }

    public double getTotalArea() {
        double total = 0;
        for (Region region : regions) {
            total += region.getTotalArea();
        }
        return total;
    }

    public List<City> getRegionalCenters() {
        List<City> centers = new ArrayList<>();
        for (Region region : regions) {
            City center = region.getCenter();
            if (center != null) {
                centers.add(center);
            }
        }
        return centers;
    }

    public void printInfo() {
        System.out.println("\n=== ИНФОРМАЦИЯ О ГОСУДАРСТВЕ ===");
        System.out.println("Название: " + name);

        System.out.println("\n1. Столица:");
        if (capital != null) {
            System.out.println("   " + capital);
        } else {
            System.out.println("   Не указана");
        }

        System.out.println("\n2. Количество областей: " + getRegionsCount());

        System.out.println("\n3. Общая площадь: " + getTotalArea() + " км²");

        System.out.println("\n4. Областные центры:");
        List<City> centers = getRegionalCenters();
        if (centers.isEmpty()) {
            System.out.println("   Нет областей");
        } else {
            for (int i = 0; i < centers.size(); i++) {
                System.out.println("   " + (i + 1) + ". " + centers.get(i).getName());
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(name, state.name) &&
                Objects.equals(regions, state.regions) &&
                Objects.equals(capital, state.capital);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, regions, capital);
    }

    @Override
    public String toString() {
        return "Государство: " + name + ", области: " + regions.size() +
                ", столица: " + (capital != null ? capital.getName() : "не указана");
    }
}
