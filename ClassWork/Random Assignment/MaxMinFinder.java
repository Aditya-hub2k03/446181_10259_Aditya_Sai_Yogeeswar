package com.yogesh.random;

import java.util.Arrays;
import java.util.List;

public class MaxMinFinder {

    public static void findMaxAndMin(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }

        int max = numbers.get(0);
        int min = numbers.get(0);

        for (int number : numbers) {
            if (number > max) {
                max = number;
            }
            if (number < min) {
                min = number;
            }
        }

        System.out.println("Maximum value: " + max);
        System.out.println("Minimum value: " + min);
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
        findMaxAndMin(numbers);
    }
}
