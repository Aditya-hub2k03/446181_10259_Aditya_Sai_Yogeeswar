package com.yogesh.schoolmanagement;

import java.util.*;

public class School {
	private ArrayList<Student> students;
	private ArrayList<Teacher>teachers;
	private ArrayList<SchoolClass>classes;
	
	public School() {
		this.classes=new ArrayList<>();
		this.teachers=new ArrayList<>();
		this.students=new ArrayList<>();
		
	}
	 public void addStudent(Student student) {
	        students.add(student);
	    }

	    public void removeStudent(Student student) {
	        students.remove(student);
	    }

	    public void addTeacher(Teacher teacher) {
	        teachers.add(teacher);
	    }

	    public void removeTeacher(Teacher teacher) {
	        teachers.remove(teacher);
	    }

	    public void addClass(SchoolClass schoolClass) {
	        classes.add(schoolClass);
	    }

	    public void removeClass(SchoolClass schoolClass) {
	        classes.remove(schoolClass);
	    }

	    public ArrayList<Student> getStudents() {
	        return students;
	    }

	    public ArrayList<Teacher> getTeachers() {
	        return teachers;
	    }

	    public ArrayList<SchoolClass> getClasses() {
	        return classes;
	    }
	

}
