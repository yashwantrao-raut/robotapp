package com.robotapp;


public class AnyObject implements Carriable{
    private String name;
    private double weight;

    public AnyObject(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double weight() {
        return weight;
    }
}
