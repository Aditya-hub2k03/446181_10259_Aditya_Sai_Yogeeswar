package com.yogesh.happymart;

import java.util.*;

public class FrontView {
    public static void mainItems() {
        System.out.println("\n<-------------Home Page------------->");
        System.out.println("1. Grocery Items");
        System.out.println("2. Electronic Appliances");
        System.out.println("3. Mobile Phones");
        System.out.println("4. Go to Cart");
        System.out.println("5. Exit");
    }

    public static void showItems(Map<Integer, Items> map, String category) {
        System.out.println("\n<-------------" + category + "------------->");
        for (Items i : map.values()) {
            System.out.println(i);
        }
        System.out.println("Enter 0 to go back.");
    }
}
