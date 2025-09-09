package com.xmldi.fi;
public class Student {
    private int id;
    private String name;
    private Address address;
    private String[] subjects;

    public Student() {
    }


    public void show() {
        System.out.println("Student ID: " + id);
        System.out.println("Student Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Subjects:");
        for (String subject : subjects) {
            System.out.println(" - " + subject);
        }
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String[] getSubjects() {
		return subjects;
	}

	public void setSubjects(String[] subjects) {
		this.subjects = subjects;
	}
}
