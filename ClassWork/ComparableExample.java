package com.yogesh.random;
import java.util.*;

class Student implements Comparable<Student> {
    int id;
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
  
    // Natural ordering: sort by id
    @Override
    public int compareTo(Student other) {
        return this.id - other.id;  // ascending order by id
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}

public class ComparableExample {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student(3, "Rahul"));
        list.add(new Student(1, "Amit"));
        list.add(new Student(2, "Priya"));

        Collections.sort(list);  // uses compareTo()

        System.out.println("Sorted by ID (Comparable):");
        for (Student s : list) {
            System.out.println(s);
        }
    }
}
