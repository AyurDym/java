public class Main {
    public static void main(String[] args) {
        // Создаем города
        City moscow = new City("Москва", 12500000, 2511, true);
        City spb = new City("Санкт-Петербург", 5400000, 1439, false);
        City novosibirsk = new City("Новосибирск", 1620000, 502, false);
        City ekaterinburg = new City("Екатеринбург", 1490000, 495, false);
        City kazan = new City("Казань", 1257000, 425, false);
        City nizhnyNovgorod = new City("Нижний Новгород", 1252000, 460, false);
        City chelyabinsk = new City("Челябинск", 1196000, 530, false);
        City omsk = new City("Омск", 1164000, 572, false);
        City samara = new City("Самара", 1156000, 541, false);
        City rostov = new City("Ростов-на-Дону", 1137000, 348, false);

        // Создаем районы
        District centralDistrict = new District("Центральный район");
        centralDistrict.addCity(moscow);
        centralDistrict.addCity(new City("Подольск", 300000, 40, false));

        District northernDistrict = new District("Северный район");
        northernDistrict.addCity(spb);
        northernDistrict.addCity(new City("Выборг", 80000, 160, false));

        District novosibirskDistrict = new District("Новосибирский район");
        novosibirskDistrict.addCity(novosibirsk);
        novosibirskDistrict.addCity(new City("Бердск", 100000, 70, false));

        District ekaterinburgDistrict = new District("Екатеринбургский район");
        ekaterinburgDistrict.addCity(ekaterinburg);
        ekaterinburgDistrict.addCity(new City("Верхняя Пышма", 70000, 60, false));

        District kazanDistrict = new District("Казанский район");
        kazanDistrict.addCity(kazan);
        kazanDistrict.addCity(new City("Зеленодольск", 100000, 70, false));

        District nizhnyNovgorodDistrict = new District("Нижегородский район");
        nizhnyNovgorodDistrict.addCity(nizhnyNovgorod);
        nizhnyNovgorodDistrict.addCity(new City("Дзержинск", 230000, 140, false));

        // Создаем области
        Region moscowRegion = new Region("Московская область");
        moscowRegion.addDistrict(centralDistrict);
        moscowRegion.addDistrict(new District("Подмосковный район"));

        Region leningradRegion = new Region("Ленинградская область");
        leningradRegion.addDistrict(northernDistrict);

        Region novosibirskRegion = new Region("Новосибирская область");
        novosibirskRegion.addDistrict(novosibirskDistrict);

        Region sverdlovskRegion = new Region("Свердловская область");
        sverdlovskRegion.addDistrict(ekaterinburgDistrict);

        Region tatarstanRegion = new Region("Республика Татарстан");
        tatarstanRegion.addDistrict(kazanDistrict);

        Region nizhnyNovgorodRegion = new Region("Нижегородская область");
        nizhnyNovgorodRegion.addDistrict(nizhnyNovgorodDistrict);

        // Создаем государство
        State russia = new State("Российская Федерация");
        russia.addRegion(moscowRegion);
        russia.addRegion(leningradRegion);
        russia.addRegion(novosibirskRegion);
        russia.addRegion(sverdlovskRegion);
        russia.addRegion(tatarstanRegion);
        russia.addRegion(nizhnyNovgorodRegion);
        russia.setCapital(moscow);

        // Выводим информацию
        russia.printInfo();

        // Демонстрация работы переопределенных методов
        System.out.println("\n=== ДЕМОНСТРАЦИЯ ПЕРЕОПРЕДЕЛЕННЫХ МЕТОДОВ ===");

        City city1 = new City("Москва", 12500000, 2511, true);
        City city2 = new City("Москва", 12500000, 2511, true);
        System.out.println("\nГорода: " + city1 + " и " + city2);
        System.out.println("city1.equals(city2): " + city1.equals(city2));
        System.out.println("hashCode city1: " + city1.hashCode());
        System.out.println("hashCode city2: " + city2.hashCode());

        Region region1 = new Region("Московская область");
        Region region2 = new Region("Московская область");
        System.out.println("\nregion1.equals(region2): " + region1.equals(region2));

        System.out.println("\n" + russia);
    }
}
