package com.yogesh.rmsv2;

import java.io.*;
import java.util.*;

public class Restaurant {
    private HashMap<Integer, FoodItem> menu = new HashMap<>();
    private HashMap<Integer, Integer> cart = new HashMap<>();
    private int nextId = 1;
    private Scanner sc = new Scanner(System.in);

    public Restaurant() {
        // Default menu items
        menu.put(nextId, new FoodItem(nextId++, "Pizza", 199.99));
        menu.put(nextId, new FoodItem(nextId++, "Burger", 99.50));
        menu.put(nextId, new FoodItem(nextId++, "Pasta", 150.0));
        menu.put(nextId, new FoodItem(nextId++, "Coffee", 80.0));
    }

    // Display Menu
    public void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.printf("%-5s %-15s %-10s\n", "ID", "Food", "Price");
        for (FoodItem item : menu.values()) {
            System.out.printf("%-5d %-15s %.2f\n", item.getId(), item.getName(), item.getPrice());
        }
    }

    // Add Food Item
    public void addFoodItem() {
        System.out.print("Enter food name: ");
        String name = sc.nextLine();
        System.out.print("Enter price: ");
        try {
            double price = Double.parseDouble(sc.nextLine());
            menu.put(nextId, new FoodItem(nextId++, name, price));
            System.out.println("Food item added.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid price.");
        }
    }

    // Remove Food Item
    public void removeFoodItem() {
        System.out.print("Enter food ID to remove: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            if (menu.remove(id) != null) {
                System.out.println("Food item removed.");
            } else {
                System.out.println("Food item not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    // Update Food Item
    public void updateFoodItem() {
        System.out.print("Enter food ID to update: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            FoodItem item = menu.get(id);
            if (item == null) {
                System.out.println("Food item not found.");
                return;
            }
            System.out.print("Enter new name: ");
            String name = sc.nextLine();
            System.out.print("Enter new price: ");
            double price = Double.parseDouble(sc.nextLine());
            menu.put(id, new FoodItem(id, name, price));
            System.out.println("Food item updated.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    // Import Menu from File
    public void importMenuFromTxt() {
        try (BufferedReader br = new BufferedReader(new FileReader("menu.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    try {
                        String name = parts[0].trim();
                        double price = Double.parseDouble(parts[1].trim());
                        menu.put(nextId, new FoodItem(nextId++, name, price));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price for " + parts[0]);
                    }
                }
            }
            System.out.println("Menu imported from menu.txt");
        } catch (IOException e) {
            System.out.println("Error reading menu.txt");
        }
    }

    // Place Order
    public void placeOrder() {
        displayMenu();
        System.out.print("Enter item ID to add to cart: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            if (!menu.containsKey(id)) {
                System.out.println("Item ID not found.");
                return;
            }
            System.out.print("Enter quantity: ");
            int qty = Integer.parseInt(sc.nextLine());
            if (qty <= 0) {
                System.out.println("Quantity must be positive.");
                return;
            }
            cart.put(id, cart.getOrDefault(id, 0) + qty);
            System.out.println("Added to cart.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }

    // Generate Bill
    public void generateBill() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("\n-------------------------------");
        System.out.printf("%-20s %10s\n", "Food", "Price");
        System.out.println("-------------------------------");

        double total = 0;
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            FoodItem item = menu.get(entry.getKey());
            int qty = entry.getValue();
            double lineTotal = item.getPrice() * qty;
            System.out.printf("%-20s %10.2f\n", item.getName() + " x" + qty, lineTotal);
            total += lineTotal;
        }

        System.out.println("-------------------------------");
        System.out.printf("%-20s %10.2f\n", "Total", total);
        System.out.println("-------------------------------");

        cart.clear(); // Empty cart after bill
    }
}
