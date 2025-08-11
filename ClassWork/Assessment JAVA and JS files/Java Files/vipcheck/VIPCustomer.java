package com.yogesh.vipcheck;

public class VIPCustomer extends Customer {
    private int discountPercentage;

    public VIPCustomer(String name, String address, String phone, int discountPercentage) {
        super(name, address, phone);
        this.discountPercentage = discountPercentage;
    }

    @Override
    public int calculateBill(int amount) {
        int discountAmount = (amount * discountPercentage) / 100;
        return amount - discountAmount;
    }
}