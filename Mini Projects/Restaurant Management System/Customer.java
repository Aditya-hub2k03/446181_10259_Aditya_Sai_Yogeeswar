package com.yogesh.rmsv1;
import java.io.*;
import java.util.*;

public class Customer {
    private Menu menu;
    private static int billCounter = 1;
    private HashMap<Integer, Integer> order; // ItemID -> Quantity
    private HashMap<String, Integer> coupons;

    public Customer(Menu menu) {
        this.menu = menu;
        order = new HashMap<>();
        coupons = new HashMap<>();
        coupons.put("DISC10", 10);
        coupons.put("DISC20", 20);
    }

    public void customerMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Show Menu");
            System.out.println("2. Place Order");
            System.out.println("3. Pay Bill");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    menu.displayMenu();
                    break;
                case 2:
                    placeOrder(sc);
                    break;
                case 3:
                    payBill(sc);
                    break;
                case 4:
                    System.out.println("Exiting Customer...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    private void placeOrder(Scanner sc) {
        while (true) {
            menu.displayMenu();
            System.out.print("Enter Item ID to order: ");
            int id = Integer.parseInt(sc.nextLine());
            if (menu.containsItem(id)) {
                System.out.print("Enter Quantity: ");
                int qty = Integer.parseInt(sc.nextLine());
                order.put(id, order.getOrDefault(id, 0) + qty);
            } else {
                System.out.println("Invalid Item ID!");
            }
            System.out.print("Do you want to add more items? (yes/no): ");
            String more = sc.nextLine();
            if (!more.equalsIgnoreCase("yes")) break;
        }
    }

    private void payBill(Scanner sc) {
        if (order.isEmpty()) {
            System.out.println("No items in the order!");
            return;
        }

        double total = 0;
        System.out.println("\n--- Bill ---");
        for (Map.Entry<Integer, Integer> entry : order.entrySet()) {
            int id = entry.getKey();
            int qty = entry.getValue();
            String[] item = menu.getItem(id);
            double price = Double.parseDouble(item[1]);
            double cost = qty * price;
            total += cost;
            System.out.printf("%s x%d = %.2f\n", item[0], qty, cost);
        }
        System.out.println("Total: " + total);

        System.out.print("Do you want to apply a coupon? (yes/no): ");
        String apply = sc.nextLine();
        if (apply.equalsIgnoreCase("yes")) {
            System.out.print("Enter Coupon Code: ");
            String code = sc.nextLine();
            if (coupons.containsKey(code)) {
                int discount = coupons.get(code);
                total = total - (total * discount / 100.0);
                System.out.println("Coupon applied! Discount: " + discount + "%");
            } else {
                System.out.println("Invalid coupon!");
            }
        }

        System.out.println("Final Amount to Pay: " + total);
        saveBillToFile(total);
        order.clear();
    }

    private void saveBillToFile(double total) {
        try {
            File folder = new File("bills");
            if (!folder.exists()) folder.mkdir();

            FileWriter writer = new FileWriter("bills/bill" + billCounter + ".txt");
            writer.write("----- Bill " + billCounter + " -----\n");
            for (Map.Entry<Integer, Integer> entry : order.entrySet()) {
                int id = entry.getKey();
                int qty = entry.getValue();
                String[] item = menu.getItem(id);
                double price = Double.parseDouble(item[1]);
                double cost = qty * price;
                writer.write(item[0] + " x" + qty + " = " + cost + "\n");
            }
            writer.write("Total = " + total + "\n");
            writer.close();
            System.out.println("Bill saved as bills/bill" + billCounter + ".txt");
            billCounter++;
        } catch (IOException e) {
            System.out.println("Error saving bill: " + e.getMessage());
        }
    }
}
