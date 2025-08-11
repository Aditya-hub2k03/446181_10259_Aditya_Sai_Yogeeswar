package com.yogesh.arrayrepeatremove;

import java.util.*; //Arrays,Scanner

public class RemoveElementFromArray {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of elements: ");
        int n = sc.nextInt();

        int[] arr = new int[n];
        
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        System.out.print("Enter the element to remove: ");
        int target = sc.nextInt();

        int count = 0;
        for (int num : arr) {
            if (num != target) {
                count++;
            }
        }

        int[] newArr = new int[count];
        int index = 0;
        for (int num : arr) {
            if (num != target) {
                newArr[index++] = num;
            }
        }

        System.out.println("Array after removing all occurrences of " + target + ": " 
                           + Arrays.toString(newArr));

        sc.close();
    }
}
