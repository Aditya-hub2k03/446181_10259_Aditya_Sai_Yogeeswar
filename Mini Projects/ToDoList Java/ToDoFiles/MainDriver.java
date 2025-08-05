package com.aditya.todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainDriver {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        SearchManager manager = new SearchManager();
        boolean running = true;

        while (running) {
            System.out.println("\n===== To-Do List Java Application =====");
            System.out.println("Current Date & Time: " + LocalDateTime.now().format(formatter));
            System.out.println("1. Show All Tasks");
            System.out.println("2. Add Task");
            System.out.println("3. Edit Task");
            System.out.println("4. Update Task Status");
            System.out.println("5. Search Tasks");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> manager.printAllTasks();
                case 2 -> {
                    System.out.print("Enter task description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Enter target date & time (yyyy-MM-dd HH:mm:ss): ");
                    LocalDateTime target = LocalDateTime.parse(scanner.nextLine(), formatter);
                    manager.addTask(desc, target);
                }
                case 3 -> {
                    System.out.print("Enter task ID to edit: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new description (leave blank to keep same): ");
                    String newDesc = scanner.nextLine();
                    System.out.print("Enter new target date & time (leave blank to keep same): ");
                    String dateInput = scanner.nextLine();
                    LocalDateTime newDate = dateInput.isEmpty() ? null : LocalDateTime.parse(dateInput, formatter);
                    manager.editTask(id, newDesc, newDate);
                }
                case 4 -> {
                    System.out.print("Enter task ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter status (Pending/In Progress/Completed): ");
                    String status = scanner.nextLine();
                    manager.updateStatus(id, status);
                }
                case 5 -> {
                    System.out.println("Search by: 1) ID  2) Keyword  3) Date Range  4) Status");
                    int searchOpt = scanner.nextInt();
                    scanner.nextLine();
                    switch (searchOpt) {
                        case 1 -> {
                            System.out.print("Enter task ID: ");
                            manager.searchById(scanner.nextInt());
                        }
                        case 2 -> {
                            System.out.print("Enter keyword: ");
                            manager.searchByKeyword(scanner.nextLine());
                        }
                        case 3 -> {
                            System.out.print("Enter start date & time: ");
                            LocalDateTime start = LocalDateTime.parse(scanner.nextLine(), formatter);
                            System.out.print("Enter end date & time: ");
                            LocalDateTime end = LocalDateTime.parse(scanner.nextLine(), formatter);
                            manager.searchByDateRange(start, end);
                        }
                        case 4 -> {
                            System.out.print("Enter status: ");
                            manager.searchByStatus(scanner.nextLine());
                        }
                        default -> System.out.println("Invalid search option.");
                    }
                }
                case 6 -> {
                    running = false;
                    System.out.println("Exiting application...");
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
