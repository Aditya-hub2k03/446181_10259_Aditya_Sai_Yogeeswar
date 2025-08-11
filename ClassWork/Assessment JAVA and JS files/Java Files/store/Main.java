package com.yogesh.store;

public class Main {
    public static void main(String[] args) {
        Store store = new Store(101, "Gadget Hub");

        store.addProduct("Laptop", 55000, 2);
        store.addProduct("Smartphone", 25000, 3);
        store.addProduct("Headphones", 2000, 5);

        store.displayStoreInfo();
    }
}