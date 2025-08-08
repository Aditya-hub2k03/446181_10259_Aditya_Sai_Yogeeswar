package com.yogesh.schoolmanagement;

import java.util.*;

public class SchoolClass {
	private String className;
	private Teacher teacher;
	private ArrayList<Student>students;
	
	public SchoolClass(String className, Teacher teacher) {
		this.className=className;
		this.teacher=teacher;
		this.students=new ArrayList<>();
		
	}

	 public String getClassName() {
	        return className;
	    }

	    public void setClassName(String className) {
	        this.className = className;
	    }

	    public Teacher getTeacher() {
	        return teacher;
	    }

	    public void setTeacher(Teacher teacher) {
	        this.teacher = teacher;
	    }

	    public void addStudent(Student student) {
	        students.add(student);
	    }

	    public void removeStudent(Student student) {
	        students.remove(student);
	    }

	    public ArrayList<Student> getStudents() {
	        return students;
	    }

}
