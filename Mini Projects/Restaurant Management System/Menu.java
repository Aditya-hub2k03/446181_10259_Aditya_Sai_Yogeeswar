package com.yogesh.rmsv1;

import java.io.*;
import java.util.*;

public class Menu {
    private static final HashMap<Integer, MenuItem> menu = new HashMap<>();

    public static void addItem(int id, String name, double price) {
        menu.put(id, new MenuItem(id, name, price));
    }

    public static void updateItem(int id, String name, double price) {
        if (menu.containsKey(id)) {
            menu.put(id, new MenuItem(id, name, price));
            System.out.println("Item updated successfully!");
        } else {
            System.out.println("Item not found!");
        }
    }

    public static void deleteItem(int id) {
        if (menu.remove(id) != null) {
            System.out.println("Item deleted successfully!");
        } else {
            System.out.println("Item not found!");
        }
    }

    public static void loadMenuFromFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            menu.clear();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                menu.put(id, new MenuItem(id, name, price));
            }
            System.out.println("Menu loaded successfully!");
        } catch (Exception e) {
            System.out.println("Error loading menu: " + e.getMessage());
        }
    }

    public static void showMenu() {
        System.out.println("\n###############################################################");
        System.out.println("# ItemID #       Name of the Item        #     Item Price     #");
        System.out.println("###############################################################");
        for (MenuItem item : menu.values()) {
            System.out.printf("# %-6d # %-25s # %-17.2f #\n", item.id, item.name, item.price);
        }
        System.out.println("###############################################################");
    }

    public static boolean containsItem(int id) {
        return menu.containsKey(id);
    }

    public static MenuItem getItem(int id) {
        return menu.get(id);
    }
}
