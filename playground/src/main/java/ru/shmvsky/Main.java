package ru.shmvsky;

import ru.shmvsky.compare.Car;
import ru.shmvsky.compare.CarComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        checkComparators();
    }

    public static void checkComparators() {
        List<Car> cars = new ArrayList<>();

        cars.add(new Car("Toyota", "2015"));
        cars.add(new Car("Honda", "2017"));
        cars.add(new Car("Ford", "2016"));
        cars.add(new Car("BMW", "2018"));
        cars.add(new Car("Audi", "2015"));
        cars.add(new Car("Toyota", "2018"));
        cars.add(new Car("Honda", "2015"));
        cars.add(new Car("Ford", "2018"));
        cars.add(new Car("BMW", "2015"));
        cars.add(new Car("Audi", "2017"));

        //same
        Collections.sort(cars);

        //same
        cars.sort(new CarComparator());

        cars.forEach(System.out::println);
    }

}