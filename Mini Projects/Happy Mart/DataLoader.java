package com.yogesh.happymart;
import java.io.*;
import java.util.*;

public class DataLoader {
    public static Map<Integer, Items> loadItems(String filePath) {
        Map<Integer, Items> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line; 
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String brand = parts[2];
                String property = parts[3];
                int price = Integer.parseInt(parts[4]);
                map.put(id, new Items(id, name, brand, property, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
