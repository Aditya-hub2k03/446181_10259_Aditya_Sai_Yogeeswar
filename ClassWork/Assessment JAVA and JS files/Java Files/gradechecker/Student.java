package com.yogesh.gradechecker;

public class Student {
	private String student_name;
	private int rollNo;
	private int marks;
	
	public Student(String student_name, int rollNo, int marks) {
		this.student_name = student_name;
		this.rollNo = rollNo;
		this.marks = marks;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public int getRollNo() {
		return rollNo;
	}

	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}
	
	public String calculateGrade() {
        if (marks >= 90) {
            return "A";
        } else if (marks >= 80) {
            return "B";
        } else if (marks >= 70) {
            return "C";
        } else if (marks >= 60) {
            return "D";
        } else {
            return "F";
        }
    }



public void displayGrade() {
    String grade = calculateGrade();
    System.out.println("Student Name: " + student_name + ", Roll No: " + rollNo + ", Grade: " + grade);
}

}