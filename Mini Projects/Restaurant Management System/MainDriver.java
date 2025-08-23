package com.yogesh.rmsv1;
import java.util.Scanner;

public class MainDriver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();

        int choice; 
        do {
            System.out.println("\n--- Welcome to Restaurant Management System ---");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    Customer cust = new Customer(menu);
                    cust.customerMenu(sc);
                    break;
                case 2:
                    Admin admin = new Admin(menu);
                    if (admin.login(sc)) {
                        admin.adminMenu(sc);
                    } else {
                        System.out.println("Invalid login!");
                    }
                    break;
                case 3:
                    System.out.println("Thank you for using Restaurant Management System!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 3);

        sc.close();
    }
}
