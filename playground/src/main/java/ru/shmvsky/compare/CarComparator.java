package ru.shmvsky.compare;

import java.util.Comparator;

public class CarComparator implements Comparator<Car> {

    @Override
    public int compare(Car o1, Car o2) {

        int res = o1.getModel().compareTo(o2.getModel());
        if (res == 0) {
            res = o1.getYear().compareTo(o2.getYear());
        }

        return res;
    }



}
