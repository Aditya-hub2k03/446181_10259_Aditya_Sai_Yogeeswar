package com.yogesh.happymart;

import java.util.*;

public class Ymart {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Load Data
        Map<Integer, Items> food = DataLoader.loadItems("D:/data/food.txt");
        Map<Integer, Items> electronics = DataLoader.loadItems("D:/data/electronics.txt");
        Map<Integer, Items> mobiles = DataLoader.loadItems("D:/data/mobiles.txt");

        List<Items> cart = new ArrayList<>();

        // Login
        System.out.println("\n<-------------LOGIN PAGE------------->");
        System.out.print("Enter Your Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Your Password: ");
        String pass = sc.nextLine();

        if(!(email.equals("user@gmail.com") && pass.equals("user123"))) {
            System.out.println("Invalid Login!");
            return;
        }

        System.out.println("\n...............Welcome to Happy Mart...............");
        int choice;
        do {
            FrontView.mainItems();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch(choice) {
                case 1: Browsing.browse(food, cart, sc, "Food Items"); break;
                case 2: Browsing.browse(electronics, cart, sc, "Electronic Items"); break;
                case 3: Browsing.browse(mobiles, cart, sc, "Mobile Phones"); break;
                case 4: BillGenerator.generateBill(cart, sc); break;
                case 5: System.out.println("Exiting... Thank You!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while(choice != 5);

        sc.close();
    }
}
