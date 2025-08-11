package com.yogesh.bookstore;

import java.util.*;

public class BookStore {
    public static void main(String[] args) {
        List<Books> books = new ArrayList<>();

        books.add(new Books("Java Basics", "Aditya", 450, 10));
        books.add(new Books("Python Programming", "Sai", 550, 5));
        books.add(new Books("Data Structures", "Yogeeswar", 600, 8));

        System.out.println("Available Books");
        for (Books b : books) {
            b.displayInfo();
        }

        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter the title of the book you want: ");
        String searchTitle = sc.nextLine();

        System.out.print("Enter number of copies you want: ");
        int qty = sc.nextInt();

        boolean found = false;

        for (Books b : books) {
            if (b.getTitle().equalsIgnoreCase(searchTitle)) {
                found = true;
                if (b.getNoOfBooks() >= qty) {
                    int totalCost = qty * b.getCost();
                    System.out.printf("Total cost for %d copies of '%s': %d%n",
                            qty, b.getTitle(), totalCost);

                    b.setNoOfBooks(b.getNoOfBooks() - qty);
                    System.out.println("Purchase successful! Remaining stock: " + b.getNoOfBooks());
                } else {
                    System.out.println("Sorry, only " + b.getNoOfBooks() + " copies available.");
                }
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found in the store.");
        }

        sc.close();
    }
}