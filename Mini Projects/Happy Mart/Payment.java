package com.yogesh.happymart;

public class Payment implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("Wait... Your Payment is Processing...");
            Thread.sleep(3000);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
