package com.yogesh.random;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DuplicateNumberChecker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<Integer> numberSet = new HashSet<>();
        System.out.println("Enter a list of integers separated by spaces:");

        String inputLine = scanner.nextLine();
        String[] numbersStr = inputLine.split(" ");

        try {
            for (String numStr : numbersStr) {
                int number = Integer.parseInt(numStr);
                if (numberSet.contains(number)) {
                    throw new IllegalArgumentException("Duplicate number found: " + number);
                }
                numberSet.add(number);
            }
            System.out.println("No duplicates found in the list.");
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter only integers separated by spaces.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
