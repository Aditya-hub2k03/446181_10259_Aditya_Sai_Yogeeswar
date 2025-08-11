package com.yogesh.vipcheck;

public class Main {
    public static void main(String[] args) {
        Customer c1 = new Customer("Aditya", "VSKP", "9000850696");

        Customer c2 = new VIPCustomer("Yogeeswar", "CHN", "8106400036", 20);

        System.out.println("Normal Customer");
        c1.displayCustomerInfo();
        System.out.println("Bill Amount: " + c1.calculateBill(5000));

        System.out.println("\n VIP Customer ");
        c2.displayCustomerInfo();
        System.out.println("Bill Amount after discount: " + c2.calculateBill(5000));
    }
}