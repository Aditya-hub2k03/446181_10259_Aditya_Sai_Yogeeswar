package com.yogesh.gradechecker;

public class Main {
    public static void main(String[] args) {
        Student[] students = {
            new Student("Aditya", 101, 85),
            new Student("Sai ", 102, 92),
            new Student("Yogeeswar", 103, 68),
            new Student("Pyda", 104, 55),
            new Student("ASYP", 105, 78)
        };

        for (Student student : students) {
            student.displayGrade();
        }
    }
}
