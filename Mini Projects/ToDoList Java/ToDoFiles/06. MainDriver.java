package com.aditya.todo;

import java.util.*;
import java.text.SimpleDateFormat;

public class MainDriver {
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        SearchManager manager = new SearchManager();
        boolean running = true;

        System.out.println("Current Date & Time: " + dateTimeFormatter.format(new Date()));

        while (running) {
            System.out.println("\nTo-Do List Menu:");
            System.out.println("1. Add Task");
            System.out.println("2. Edit Task");
            System.out.println("3. Update Status");
            System.out.println("4. View All Tasks");
            System.out.println("5. Search Task");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter description: ");
                        String desc = scanner.nextLine();
                        System.out.print("Enter added date (yyyy-MM-dd): ");
                        Date addedDate = dateFormatter.parse(scanner.nextLine());
                        System.out.print("Enter target date and time (yyyy-MM-ddTHH:mm): ");
                        Date targetDateTime = dateTimeFormatter.parse(scanner.nextLine());
                        manager.addTask(desc, addedDate, targetDateTime);
                        break;

                    case 2:
                        System.out.print("Enter task ID: ");
                        int editId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter new description: ");
                        String newDesc = scanner.nextLine();
                        System.out.print("Enter new target date and time (yyyy-MM-ddTHH:mm): ");
                        Date newTarget = dateTimeFormatter.parse(scanner.nextLine());
                        manager.editTask(editId, newDesc, newTarget);
                        break;

                    case 3:
                        System.out.print("Enter task ID: ");
                        int statusId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter new status: ");
                        String newStatus = scanner.nextLine();
                        manager.updateStatus(statusId, newStatus);
                        break;

                    case 4:
                        manager.printAllTasks();
                        break;

                    case 5:
                        System.out.println("Search by: 1-ID, 2-Keyword, 3-Date Range, 4-Status");
                        int searchType = Integer.parseInt(scanner.nextLine());
                        List<Todo> results = null;

                        if (searchType == 1) {
                            System.out.print("Enter ID: ");
                            results = manager.searchById(Integer.parseInt(scanner.nextLine()));
                        } else if (searchType == 2) {
                            System.out.print("Enter keyword: ");
                            results = manager.searchByKeyword(scanner.nextLine());
                        } else if (searchType == 3) {
                            System.out.print("Enter start date-time (yyyy-MM-ddTHH:mm): ");
                            Date start = dateTimeFormatter.parse(scanner.nextLine());
                            System.out.print("Enter end date-time (yyyy-MM-ddTHH:mm): ");
                            Date end = dateTimeFormatter.parse(scanner.nextLine());
                            results = manager.searchByDateRange(start, end);
                        } else if (searchType == 4) {
                            System.out.print("Enter status: ");
                            results = manager.searchByStatus(scanner.nextLine());
                        }

                        if (results != null && !results.isEmpty()) {
                            for (Todo t : results) {
                                System.out.println("ID: " + t.id + " | " + t.description);
                            }
                        } else {
                            System.out.println("No matching tasks found.");
                        }
                        break;

                    case 6:
                        running = false;
                        System.out.println("Exiting application...");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please follow the correct format.");
            }
        }
    }
}
