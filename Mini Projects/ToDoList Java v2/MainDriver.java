package com.aditya.todo;
import java.util.*;
import java.text.SimpleDateFormat;

public class MainDriver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager manager = new TaskManager();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        while (true) {
            // Show current system date/time at top
            Date now = new Date();
            System.out.println("\n==================================");
            System.out.println("   TO-DO LIST APPLICATION");
            System.out.println("   Current Date & Time: " + sdf.format(now));
            System.out.println("==================================");

            System.out.println("\n===== TO-DO LIST MENU =====");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Remove Task");
            System.out.println("4. Search Task");
            System.out.println("5. Mark Task as Completed");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter task ID: ");
                        int id = sc.nextInt(); sc.nextLine();
                        System.out.print("Enter task title: ");
                        String title = sc.nextLine();
                        System.out.print("Enter task description: ");
                        String desc = sc.nextLine();
                        System.out.print("Enter task due date (yyyy-MM-dd HH:mm): ");
                        String dateInput = sc.nextLine();

                        Date dueDate = sdf.parse(dateInput);
                        if (dueDate.before(new Date())) {
                            System.out.println("Cannot set a past due date!");
                            break;
                        }

                        manager.addTask(new Todo(id, title, desc, dueDate));
                        break;
                    case 2:
                        manager.displayTasks();
                        break;
                    case 3:
                        System.out.print("Enter task ID to remove: ");
                        int removeId = sc.nextInt();
                        manager.removeTask(removeId);
                        break;
                    case 4:
                        System.out.print("Enter keyword to search: ");
                        String keyword = sc.nextLine();
                        List<BaseTask> results = SearchManager.search(manager.getAllTasks(), keyword);
                        if (results.isEmpty()) {
                            System.out.println("No matches found.");
                        } else {
                            for (BaseTask task : results) {
                                System.out.println(task);
                            }
                        }
                        break;
                    case 5:
                        System.out.print("Enter task ID to mark completed: ");
                        int completeId = sc.nextInt();
                        manager.markCompleted(completeId);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
