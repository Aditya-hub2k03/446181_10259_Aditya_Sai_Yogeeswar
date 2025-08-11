package com.yogesh.store;

import java.util.*;

public class Store {
    private int storeID;
    private String storeName;
    private List<Product> productList;
    private int revenue;

    public Store(int storeID, String storeName) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.productList = new ArrayList<>();
        this.revenue = 0;
    }

    public void addProduct(String name, int price, int quantity) {
        Product p = new Product(name, price, quantity);
        productList.add(p);
        revenue += p.getTotalPrice();
    }

    public int calculateTotalRevenue() {
        int total = 0;
        for (Product p : productList) {
            total += p.getTotalPrice();
        }
        return total;
    }

    public void displayStoreInfo() {
        System.out.println("Store ID: " + storeID);
        System.out.println("Store Name: " + storeName);
        System.out.println("Products:");
        for (Product p : productList) {
            System.out.println(" - " + p);
        }
        System.out.println("Total Revenue: " + calculateTotalRevenue());
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}