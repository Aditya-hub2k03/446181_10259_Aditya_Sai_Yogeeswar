package com.yogesh.random;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelChunkProcessor {
    private final String inputFile;
    private final String outputDir;
    private final String outputFilePrefix;
    private final int bufferSize;
    private final AtomicInteger linesProcessed = new AtomicInteger(0);
    private int totalLines = 0;
    private List<List<String>> chunks = Collections.synchronizedList(new ArrayList<>());

    public ParallelChunkProcessor(String inputFile, String outputDir, String outputFilePrefix, int bufferSize) {
        this.inputFile = inputFile;
        this.outputDir = outputDir;
        this.outputFilePrefix = outputFilePrefix;
        this.bufferSize = bufferSize;
    }

    public void start() {
        System.out.print("Counting total lines and splitting into chunks");
        Thread countingThread = new Thread(this::splitIntoChunks);
        Thread spinnerThread = new Thread(() -> {
            while (chunks.isEmpty() && countingThread.isAlive()) {
                System.out.print(".");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        countingThread.start();
        spinnerThread.start();
        try {
            countingThread.join();
            spinnerThread.interrupt();
            System.out.print("\rCounting total lines and splitting into chunks... Done!\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (chunks.isEmpty()) {
            System.err.println("No chunks were created. Check if the input file is empty or accessible.");
            return;
        }

        createOutputDirectory();
        processChunks();
    }

    private void splitIntoChunks() {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            List<String> currentChunk = new ArrayList<>(bufferSize);
            String line;
            while ((line = br.readLine()) != null) {
                currentChunk.add(line);
                totalLines++;
                if (currentChunk.size() == bufferSize) {
                    chunks.add(new ArrayList<>(currentChunk));
                    currentChunk.clear();
                }
            }
            if (!currentChunk.isEmpty()) {
                chunks.add(new ArrayList<>(currentChunk));
            }
        } catch (IOException e) {
            System.err.println("\nError splitting file: " + e.getMessage());
        }
    }

    private void createOutputDirectory() {
        File dir = new File(outputDir);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.err.println("Failed to create output directory: " + outputDir);
            } else {
                System.out.println("Created output directory: " + outputDir);
            }
        }
    }

    private void processChunks() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<?>> futures = new ArrayList<>();
        AtomicInteger chunkCounter = new AtomicInteger(1);

        for (List<String> chunk : chunks) {
            futures.add(executor.submit(() -> {
                int chunkNumber = chunkCounter.getAndIncrement();
                String outputFileName = outputDir + File.separator + outputFilePrefix + "_" + chunkNumber + ".txt";
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
                    for (String chunkLine : chunk) {
                        bw.write(chunkLine);
                        bw.newLine();
                    }
                    linesProcessed.addAndGet(chunk.size());
                } catch (IOException e) {
                    System.err.println("Error writing chunk " + chunkNumber + ": " + e.getMessage());
                }
            }));
        }

        // Progress tracking
        new Thread(() -> {
            while (!futures.stream().allMatch(Future::isDone)) {
                int progress = (int) ((linesProcessed.get() * 100.0) / totalLines);
                System.out.printf("\rProcessing: %d%%", progress);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("\nProcessing complete.");
        }).start();

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter input file path: ");
        String inputFile = scanner.nextLine();
        System.out.print("Enter directory path to save output files: ");
        String outputDir = scanner.nextLine();
        System.out.print("Enter output file prefix (e.g., 'output'): ");
        String outputFilePrefix = scanner.nextLine();
        System.out.print("Enter buffer size (number of lines per chunk): ");
        int bufferSize = scanner.nextInt();
        scanner.close();

        ParallelChunkProcessor processor = new ParallelChunkProcessor(inputFile, outputDir, outputFilePrefix, bufferSize);
        processor.start();
    }
}
