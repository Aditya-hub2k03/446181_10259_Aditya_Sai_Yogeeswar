package com.yogesh.rmsv1;

import java.util.Scanner;

public class Admin {
    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PASS = "password";
    private static final Scanner sc = new Scanner(System.in);

    public static void login() {
        System.out.print("Enter Admin ID: ");
        String id = sc.next();
        System.out.print("Enter Password: ");
        String pass = sc.next();

        if (id.equals(ADMIN_ID) && pass.equals(ADMIN_PASS)) {
            System.out.println("Login Successful!");
            adminMenu();
        } else {
            System.out.println("Invalid Credentials!");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n====== Admin Menu ======");
            System.out.println("1. View Menu");
            System.out.println("2. Add Item");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("5. Load Menu from File");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    Menu.showMenu();
                    break;
                case 2:
                    addItem();
                    break;
                case 3:
                    updateItem();
                    break;
                case 4:
                    deleteItem();
                    break;
                case 5:
                    loadMenuFromFile();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addItem() {
        System.out.print("Enter Item ID: ");
        int id = sc.nextInt();
        sc.nextLine(); 
        System.out.print("Enter Item Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Item Price: ");
        double price = sc.nextDouble();

        Menu.addItem(id, name, price);
        System.out.println("Item added successfully!");
    }

    private static void updateItem() {
        System.out.print("Enter Item ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter New Name: ");
        String name = sc.nextLine();
        System.out.print("Enter New Price: ");
        double price = sc.nextDouble();

        Menu.updateItem(id, name, price);
    }

    private static void deleteItem() {
        System.out.print("Enter Item ID to delete: ");
        int id = sc.nextInt();
        Menu.deleteItem(id);
    }

    private static void loadMenuFromFile() {
        sc.nextLine();
        System.out.print("Enter file path: ");
        String path = sc.nextLine();
        Menu.loadMenuFromFile(path);
    }
}
