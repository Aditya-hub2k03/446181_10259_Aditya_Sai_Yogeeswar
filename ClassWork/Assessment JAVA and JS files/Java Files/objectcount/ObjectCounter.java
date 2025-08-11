package com.yogesh.objectcount;

public class ObjectCounter {

    private static int objectCount = 0;

    public ObjectCounter() {
        objectCount++;
    }

    public static void display() {
        System.out.println("Number of objects created: " + objectCount);
    }

    public static void main(String[] args) {
        ObjectCounter obj1 = new ObjectCounter();
        ObjectCounter obj2 = new ObjectCounter();
        ObjectCounter obj3 = new ObjectCounter();
        ObjectCounter.display();
    }
}
