package com.yogesh.flightbookingsystem;

public class Admin extends User{
	public Admin(String name){
		super(name);
		
	}
	
	@Override
	public void showMenu() {
		System.out.println("Welcome "+name+" - Flight Booking System");
		System.out.println("1. Register Passenger");
		System.out.println("2. View Flights");
		System.out.println("3. Book a Flight");
		System.out.println("4. View Bookings");
		System.out.println("5. Search Flights");
		System.out.println("6. Exit");
	}

}
