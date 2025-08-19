package com.yogesh.happymart;
import java.util.*;

public class Browsing {
    public static void browse(Map<Integer, Items> map, List<Items> cart, Scanner sc, String category) {
        FrontView.showItems(map, category);
        while(true) {
            System.out.print("Enter Item ID to add to cart (0 to stop): ");
            int choice = sc.nextInt();
            if(choice == 0) break;
            if(map.containsKey(choice)) {
                cart.add(map.get(choice));
                System.out.println("Added: " + map.get(choice).getName());
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
