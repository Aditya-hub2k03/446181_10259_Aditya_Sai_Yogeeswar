package com.yogesh.random;

import java.io.*;
import java.util.Scanner;

class FileReaderThread extends Thread {
    private File file;
    private int linesToRead;   // number of lines user wants
    private int bufferSize;    // buffer size
    private boolean readAll;   // flag to read complete file

    public FileReaderThread(File file, int linesToRead, int bufferSize, boolean readAll) {
        this.file = file;
        this.linesToRead = linesToRead;
        this.bufferSize = bufferSize;
        this.readAll = readAll;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(file), bufferSize)) {
            String line;
            int count = 0;
            int totalLines = countLines(file);

            int targetLines = readAll ? totalLines : Math.min(linesToRead, totalLines);

            System.out.println("\n--- Reading File ---");
            while ((line = br.readLine()) != null) {
                count++;
                System.out.println(line);

                // Progress bar update
                showProgress(count, targetLines);

                if (!readAll && count >= linesToRead) {
                    break;
                }
            }
            System.out.println("\n--- Reading Completed ---");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int countLines(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int lines = 0;
            while (br.readLine() != null) lines++;
            return lines;
        }
    }

    private void showProgress(int current, int total) {
        int barLength = 50;
        double progress = (double) current / total;
        int filled = (int) (progress * barLength);

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < filled; i++) bar.append("=");
        for (int i = filled; i < barLength; i++) bar.append(" ");
        bar.append("] ");

        System.out.print("\r" + bar + String.format("%.2f", progress * 100) + "%");
    }
}

public class TextReadThreadv2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for file path
        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File not found!");
            return;
        }

        // Ask how many lines
        System.out.print("Enter number of lines to read (leave blank for full file): ");
        String lineInput = scanner.nextLine();

        boolean readAll = lineInput.trim().isEmpty();
        int linesToRead = readAll ? 0 : Integer.parseInt(lineInput);

        // Ask for buffer size
        System.out.print("Enter buffer size (default 1024): ");
        String bufferInput = scanner.nextLine();
        int bufferSize = bufferInput.trim().isEmpty() ? 1024 : Integer.parseInt(bufferInput);

        // Start thread
        FileReaderThread readerThread = new FileReaderThread(file, linesToRead, bufferSize, readAll);
        readerThread.start();
    }
}
