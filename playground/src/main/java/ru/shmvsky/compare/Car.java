package ru.shmvsky.compare;

public class Car implements Comparable<Car> {

    private String model;
    private String year;

    public Car(String model, String year) {
        this.model = model;
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public int compareTo(Car o) {
        int result = model.compareTo(o.model);
        if (result == 0) {
            result = year.compareTo(o.year);
        }
        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

}
