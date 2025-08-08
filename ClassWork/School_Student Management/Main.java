package com.yogesh.schoolmanagement;

import java.util.*;

public class Main {
	public static void main(String[] args) {
		School school = new School();

        Student student1 = new Student("Aditya", 16);
        Student student2 = new Student("yogesh", 17);

        Teacher teacher1 = new Teacher("Palak", "Java");
        Teacher teacher2 = new Teacher("Satya", "Backend");

        SchoolClass class1 = new SchoolClass("Java 101", teacher1);
        SchoolClass class2 = new SchoolClass("Backend 101", teacher2);

        class1.addStudent(student1);
        class2.addStudent(student2);

        school.addStudent(student1);
        school.addStudent(student2);
        school.addTeacher(teacher1);
        school.addTeacher(teacher2);
        school.addClass(class1);
        school.addClass(class2);

        System.out.println("Number of students in the school: " + school.getStudents().size());
        System.out.println("Number of teachers in the school: " + school.getTeachers().size());
        System.out.println("Number of classes in the school: " + school.getClasses().size());

        for (SchoolClass schoolClass : school.getClasses()) {
            System.out.println("Class: " + schoolClass.getClassName());
            System.out.println("Teacher: " + schoolClass.getTeacher().getName());
            System.out.println("Number of students: " + schoolClass.getStudents().size());
        }

        school.removeStudent(student1);
        school.removeTeacher(teacher1);
        school.removeClass(class1);

        System.out.println("Updated number of students in the school: " + school.getStudents().size());
        System.out.println("Updated number of teachers in the school: " + school.getTeachers().size());
        System.out.println("Updated number of classes in the school: " + school.getClasses().size());
	}

}
