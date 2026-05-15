package main.java.by.epam.learn;

import main.java.by.epam.learn.comparator.MedicinePriceComparator;
import main.java.by.epam.learn.entity.Medicine;
import main.java.by.epam.learn.parser.MedicineSaxParser;
import main.java.by.epam.learn.parser.MedicineDomParser;
import main.java.by.epam.learn.parser.MedicineStaxParser;
import main.java.by.epam.learn.validator.XsdValidator;
import main.java.by.epam.learn.transformer.HtmlTransformer;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String xmlPath = "resources/medicines.xml";
        String xsdPath = "resources/medicines.xsd";
        String xslPath = "resources/medicines.xsl";
        String htmlPath = "output/medicines.html";

        System.out.println("=== ЛЕКАРСТВЕННЫЕ ПРЕПАРАТЫ ===\n");

        // 1. Валидация
        System.out.println("1. ВАЛИДАЦИЯ XML ПО XSD");
        if (!XsdValidator.validate(xmlPath, xsdPath)) {
            System.out.println("Валидация не пройдена!");
            return;
        }

        List<Medicine> medicines = null;

        // 2. Парсинг SAX
        System.out.println("\n2. ПАРСИНГ SAX");
        try {
            medicines = MedicineSaxParser.parse(xmlPath);
            System.out.println("   Найдено: " + medicines.size() + " препаратов");
        } catch (Exception e) {
            System.err.println("SAX ошибка: " + e.getMessage());
        }

        // 3. Парсинг DOM
        System.out.println("\n3. ПАРСИНГ DOM");
        try {
            List<Medicine> domList = MedicineDomParser.parse(xmlPath);
            System.out.println("   Найдено: " + domList.size() + " препаратов");
        } catch (Exception e) {
            System.err.println("DOM ошибка: " + e.getMessage());
        }

        // 4. Парсинг StAX
        System.out.println("\n4. ПАРСИНГ StAX");
        try {
            List<Medicine> staxList = MedicineStaxParser.parse(xmlPath);
            System.out.println("   Найдено: " + staxList.size() + " препаратов");
        } catch (Exception e) {
            System.err.println("StAX ошибка: " + e.getMessage());
        }

        // 5. Сортировка по цене
        if (medicines != null && !medicines.isEmpty()) {
            System.out.println("\n5. СОРТИРОВКА ПО ЦЕНЕ");
            medicines.sort(new MedicinePriceComparator());
            for (Medicine m : medicines) {
                System.out.println("   " + m.getName() + " - " + m.getMinPrice() + " руб.");
            }
        }

        // 6. Преобразование в HTML
        System.out.println("\n6. ПРЕОБРАЗОВАНИЕ В HTML");
        try {
            HtmlTransformer.transform(xmlPath, xslPath, htmlPath);
        } catch (Exception e) {
            System.err.println("XSL ошибка: " + e.getMessage());
        }

        System.out.println("\n=== ГОТОВО ===");
        System.out.println("Откройте " + htmlPath);
    }
}