package com.yogesh.random;

import java.util.*;

class Student1 {
    int id;
    String name;

    Student1(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}

public class ComparatorExample {
    public static void main(String[] args) {
        List<Student1> list = new ArrayList<>();
        list.add(new Student1(3, "Rahul"));
        list.add(new Student1(1, "Amit"));
        list.add(new Student1(2, "Priya"));

        // Sort by name using Comparator
        Comparator<Student1> nameComparator = new Comparator<Student1>() {
            public int compare(Student1 s1, Student1 s2) {
                return s1.name.compareTo(s2.name);
            }
        };

        Collections.sort(list, nameComparator);

        System.out.println("Sorted by Name (Comparator):");
        for (Student1 s : list) {
            System.out.println(s);
        }
    }
}
