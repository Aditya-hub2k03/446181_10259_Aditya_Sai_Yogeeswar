package com.yogesh.rmsv2;

import java.util.Scanner;

public class RestaurantManagementSystem {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1": // Admin Menu
                    while (true) {
                        System.out.println("\n--- Admin Menu ---");
                        System.out.println("1. Add Food Item");
                        System.out.println("2. Remove Food Item");
                        System.out.println("3. Update Food Item");
                        System.out.println("4. Display Menu");
                        System.out.println("5. Import Menu from File");
                        System.out.println("6. Back");
                        System.out.print("Choose: ");
                        String adminChoice = sc.nextLine();

                        switch (adminChoice) {
                            case "1": restaurant.addFoodItem(); break;
                            case "2": restaurant.removeFoodItem(); break;
                            case "3": restaurant.updateFoodItem(); break;
                            case "4": restaurant.displayMenu(); break;
                            case "5": restaurant.importMenuFromTxt(); break;
                            case "6": gotoMainMenu(); break;
                            default: System.out.println("Invalid choice.");
                        }
                    }

                case "2": // Customer Menu
                    while (true) {
                        System.out.println("\n--- Customer Menu ---");
                        System.out.println("1. View Menu");
                        System.out.println("2. Place Order");
                        System.out.println("3. Generate Bill");
                        System.out.println("4. Back");
                        System.out.print("Choose: ");
                        String custChoice = sc.nextLine();

                        switch (custChoice) {
                            case "1": restaurant.displayMenu(); break;
                            case "2": restaurant.placeOrder(); break;
                            case "3": restaurant.generateBill(); break;
                            case "4": gotoMainMenu(); break;
                            default: System.out.println("Invalid choice.");
                        }
                    }

                case "3":
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void gotoMainMenu() {
        throw new RuntimeException("BACK");
    }
}
