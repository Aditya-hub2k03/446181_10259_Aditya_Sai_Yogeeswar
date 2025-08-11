package com.yogesh.store;

import java.util.*;

class Product {
    String name;
    int price;
    int quantity;

    Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    int getTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return name + " (Price: " + price + ", Qty: " + quantity + ", Total: " + getTotalPrice() + ")";
    }
}