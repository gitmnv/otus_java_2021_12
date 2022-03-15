package ru.otus.model;

public class MeasurementTemp {
    private String name;
    private double value;

    public MeasurementTemp(){}

    public MeasurementTemp(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MeasurementTemp{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
