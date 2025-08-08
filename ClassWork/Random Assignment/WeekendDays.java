package com.yogesh.random;

import java.util.Scanner;

enum Weekend {
 SATURDAY,
 SUNDAY
}

public class WeekendDays {
 public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);

     System.out.println("Enter a day of the weekend (SATURDAY or SUNDAY):");
     String inputDay = scanner.nextLine().toUpperCase();

     try {
         Weekend day = Weekend.valueOf(inputDay);

         switch (day) {
             case SATURDAY:
                 System.out.println("Today is Saturday. Enjoy your weekend!");
                 break;
             case SUNDAY:
                 System.out.println("Today is Sunday. Enjoy your weekend!");
                 break;
         }
     } catch (IllegalArgumentException e) {
         System.err.println("Invalid input. Please enter a valid weekend day: SATURDAY or SUNDAY.");
     } finally {
         scanner.close();
     }
 }
}
