package com.yogesh.random;

import java.io.*;
import java.util.Scanner;

public class TextRepeater {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the input file path: ");
        String inputFilePath = scanner.nextLine();

        System.out.print("Enter the output file path: ");
        String outputFilePath = scanner.nextLine();

        System.out.print("Enter number of times to repeat: ");
        int times = scanner.nextInt();

        try {
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append(System.lineSeparator());
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                for (int i = 1; i <= times; i++) {
                    writer.write(content.toString());
                    writer.newLine();

                    printProgressBar(i, times);
                }
            }

            System.out.println("\n✅ Done! Content saved to " + outputFilePath);

        } catch (IOException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }

        scanner.close();
    }

    // Function to print progress bar in console
    private static void printProgressBar(int current, int total) {
        int barLength = 50; // length of the bar
        int filledLength = (int) ((double) current / total * barLength);

        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < filledLength; i++) bar.append("█");
        for (int i = filledLength; i < barLength; i++) bar.append("-");

        int percent = (int) ((double) current / total * 100);

        System.out.print("\r[" + bar + "] " + percent + "% (" + current + "/" + total + ")");
    }
}
