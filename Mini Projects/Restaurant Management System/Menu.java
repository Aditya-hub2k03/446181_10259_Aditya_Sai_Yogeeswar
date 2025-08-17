package com.yogesh.rmsv1;
import java.io.*;
import java.util.*;

public class Menu {
    private TreeMap<Integer, String[]> items; // Key: ItemID, Value: [ItemName, Price]
    private String menuFilePath = "D:/menu.txt";

    public Menu() {
        items = new TreeMap<>();
        preloadMenu();
    }

    private void preloadMenu() {
        File file = new File(menuFilePath);
        if (file.exists()) {
            loadMenuFromFile(menuFilePath);
        } else {
            System.out.println("No default menu.txt found. You can load one via Admin.");
        }
    }

    public void loadMenuFromFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String price = parts[2].trim();

                    // Only add if not already present
                    if (!items.containsKey(id)) {
                        items.put(id, new String[]{name, price});
                    }
                }
            }
            System.out.println("Menu loaded and merged successfully from " + path);
        } catch (IOException e) {
            System.out.println("Error loading menu file: " + e.getMessage());
        }
    }

    public void saveMenuToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(menuFilePath))) {
            for (Map.Entry<Integer, String[]> entry : items.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue()[0] + "," + entry.getValue()[1]);
                bw.newLine();
            }
            System.out.println("Menu saved back to " + menuFilePath);
        } catch (IOException e) {
            System.out.println("Error saving menu file: " + e.getMessage());
        }
    }

    public void displayMenu() {
        System.out.println("###############################################################");
        System.out.println("# ItemID # Name of the Item              # Item Price         #");
        System.out.println("###############################################################");
        for (Map.Entry<Integer, String[]> entry : items.entrySet()) {
            System.out.printf("# %-6d # %-28s # %-18s #\n",
                    entry.getKey(),
                    entry.getValue()[0],
                    entry.getValue()[1]);
        }
        System.out.println("###############################################################");
    }

    public void addItem(int id, String name, String price) {
        items.put(id, new String[]{name, price});
        System.out.println("Item added successfully!");
    }

    public void updateItem(int id, String newName, String newPrice) {
        if (items.containsKey(id)) {
            items.put(id, new String[]{newName, newPrice});
            System.out.println("Item updated successfully!");
        } else {
            System.out.println("Item not found!");
        }
    }

    public void deleteItem(int id) {
        if (items.containsKey(id)) {
            items.remove(id);
            System.out.println("Item deleted successfully!");
        } else {
            System.out.println("Item not found!");
        }
    }

    public boolean containsItem(int id) {
        return items.containsKey(id);
    }

    public String[] getItem(int id) {
        return items.get(id);
    }

    public Set<Integer> getItemIDs() {
        return items.keySet();
    }
}
