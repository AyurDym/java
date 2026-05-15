package main.java.by.epam.learn.comparator;

import main.java.by.epam.learn.entity.Medicine;
import java.util.Comparator;

public class MedicinePriceComparator implements Comparator<Medicine> {
    @Override
    public int compare(Medicine m1, Medicine m2) {
        return Double.compare(m1.getMinPrice(), m2.getMinPrice());
    }
}
