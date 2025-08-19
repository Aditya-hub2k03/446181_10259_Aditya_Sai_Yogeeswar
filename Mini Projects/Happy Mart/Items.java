package com.yogesh.happymart;

public class Items {
    private int id;
    private String name;
    private String brand;
    private String property;
    private int price;

    public Items(int id, String name, String brand, String property, int price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.property = property;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getProperty() { return property; }
    public int getPrice() { return price; }

    @Override
    public String toString() {
        return id + ". " + name + " " + brand + " " + property + " Rs." + price;
    }
}
