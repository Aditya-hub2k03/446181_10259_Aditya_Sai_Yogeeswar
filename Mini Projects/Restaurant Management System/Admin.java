package com.yogesh.rmsv1;
import java.util.Scanner;

public class Admin {
    private String adminId = "admin";
    private String password = "password";
    private Menu menu;

    public Admin(Menu menu) {
        this.menu = menu;
    }

    public boolean login(Scanner sc) {
        System.out.print("Enter Admin ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();
        return id.equals(adminId) && pass.equals(password);
    }

    public void adminMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View Menu");
            System.out.println("2. Add an Item");
            System.out.println("3. Update an Item");
            System.out.println("4. Delete an Item");
            System.out.println("5. Load Menu from Path");
            System.out.println("6. Exit (Save Menu)");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    menu.displayMenu();
                    break;
                case 2:
                    System.out.print("Enter Item ID: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Item Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Item Price: ");
                    String price = sc.nextLine();
                    menu.addItem(id, name, price);
                    break;
                case 3:
                    System.out.print("Enter Item ID to update: ");
                    int uid = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter New Name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter New Price: ");
                    String newPrice = sc.nextLine();
                    menu.updateItem(uid, newName, newPrice);
                    break;
                case 4:
                    System.out.print("Enter Item ID to delete: ");
                    int did = Integer.parseInt(sc.nextLine());
                    menu.deleteItem(did);
                    break;
                case 5:
                    System.out.print("Enter file path to load menu: ");
                    String path = sc.nextLine();
                    menu.loadMenuFromFile(path);
                    break;
                case 6:
                    menu.saveMenuToFile();
                    System.out.println("Exiting Admin...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 6);
    }
}
