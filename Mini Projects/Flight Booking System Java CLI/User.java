package com.yogesh.flightbookingsystem;

abstract public class User {
	protected String name;
	public User(String name) {
		this.name = name;
		
	}
	 
	public abstract void showMenu();
	
}
 
