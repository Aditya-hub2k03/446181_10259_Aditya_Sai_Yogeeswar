package com.yogesh.rmsv1;

import java.util.Scanner;

public class MainDriver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== Restaurant Management System ======");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Choose your role: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    Customer.customerMenu();
                    break;
                case 2:
                    Admin.login();
                    break;
                case 3:
                    System.out.println("Exiting... Thank you!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
