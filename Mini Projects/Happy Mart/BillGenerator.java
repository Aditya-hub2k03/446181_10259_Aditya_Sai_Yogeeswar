package com.yogesh.happymart;

import java.util.*;

public class BillGenerator {
    public static void generateBill(List<Items> cart, Scanner sc) {
        if(cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        System.out.println("\n<-------------CART PAGE------------->");
        int total = 0;
        for (Items i : cart) {
            System.out.println(i);
            total += i.getPrice();
        }

        int gst = (int)(total * 0.02);
        int finalAmount = total + gst;

        System.out.println("\nProducts Cost = " + total);
        System.out.println("GST + Taxes (2%) = " + gst);
        System.out.println("Total = " + finalAmount);

        System.out.println("\nChoose Payment option:\n1. Card\n2. UPI");
        int payChoice = sc.nextInt();

        Payment payment = new Payment();
        Thread t = new Thread(payment);
        t.start();
        try { t.join(); } catch(Exception e) {}
        System.out.println("Payment Successful :)");
        System.out.println("Thanks for Shopping with Ymart!");
    }
}
