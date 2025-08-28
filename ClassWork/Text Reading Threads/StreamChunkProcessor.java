package com.yogesh.random;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StreamChunkProcessor {
    private final String inputFile;
    private final String outputDir;
    private final String outputFilePrefix;
    private final int bufferSize;
    private final AtomicInteger linesProcessed = new AtomicInteger(0);
    private final BlockingQueue<List<String>> chunkQueue = new LinkedBlockingQueue<>(5); // Limit memory usage
    private int totalLines = 0;
    private volatile boolean endOfFile = false;

    public StreamChunkProcessor(String inputFile, String outputDir, String outputFilePrefix, int bufferSize) {
        this.inputFile = inputFile;
        this.outputDir = outputDir;
        this.outputFilePrefix = outputFilePrefix;
        this.bufferSize = bufferSize;
    }

    public void start() {
        System.out.print("Counting total lines");
        Thread countingThread = new Thread(this::countTotalLines);
        Thread spinnerThread = new Thread(() -> {
            while (totalLines == 0 && countingThread.isAlive()) {
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
            System.out.print("\rCounting total lines... Done!\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        createOutputDirectory();

        // Start reader and writer threads
        Thread readerThread = new Thread(this::readChunks);
        ExecutorService writerExecutor = Executors.newFixedThreadPool(4); // Adjust pool size as needed

        readerThread.start();

        // Submit writer tasks
        for (int i = 0; i < 4; i++) {
            writerExecutor.submit(this::writeChunk);
        }

        // Progress tracking
        new Thread(this::showProgress).start();

        try {
            readerThread.join();
            endOfFile = true; // Signal end of file
            writerExecutor.shutdown();
            writerExecutor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void countTotalLines() {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            while (br.readLine() != null) {
                totalLines++;
            }
        } catch (IOException e) {
            System.err.println("\nError counting lines: " + e.getMessage());
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

    private void readChunks() {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            List<String> chunk = new ArrayList<>(bufferSize);
            String line;
            while ((line = br.readLine()) != null) {
                chunk.add(line);
                if (chunk.size() == bufferSize) {
                    chunkQueue.put(new ArrayList<>(chunk));
                    chunk.clear();
                }
            }
            if (!chunk.isEmpty()) {
                chunkQueue.put(new ArrayList<>(chunk));
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Reader error: " + e.getMessage());
        }
    }

    private void writeChunk() {
        try {
            while (true) {
                List<String> chunk = chunkQueue.poll(1, TimeUnit.SECONDS);
                if (chunk == null) {
                    if (endOfFile && chunkQueue.isEmpty()) {
                        break; // Exit if no more chunks and end of file
                    }
                    continue; // No chunk available, try again
                }
                String outputFileName = outputDir + File.separator + outputFilePrefix + "_" + System.currentTimeMillis() + ".txt";
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
                    for (String chunkLine : chunk) {
                        bw.write(chunkLine);
                        bw.newLine();
                    }
                    linesProcessed.addAndGet(chunk.size());
                } catch (IOException e) {
                    System.err.println("Error writing chunk: " + e.getMessage());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void showProgress() {
        while (!endOfFile || !chunkQueue.isEmpty() || linesProcessed.get() < totalLines) {
            int progress = (int) ((linesProcessed.get() * 100.0) / totalLines);
            System.out.printf("\rProcessing: %d%%", progress);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\nProcessing complete.");
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

        StreamChunkProcessor processor = new StreamChunkProcessor(inputFile, outputDir, outputFilePrefix, bufferSize);
        processor.start();
    }
}
