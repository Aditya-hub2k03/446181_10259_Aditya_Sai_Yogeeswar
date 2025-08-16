package com.yogesh.flightbookingsystem;

public class Booking {
	private Passenger passenger;
	private Flight flight;
	private String date;
	
	public Booking(Passenger passenger, Flight flight, String date){
		this.passenger = passenger;
		this.flight = flight;
		this.date = date; 
		
	}
	public void display() {
		System.out.println("Passenger: "+passenger.getName() + "("+passenger.getContact()+")");
		System.out.println("Flight: "+flight.getFlightNo()+" - "+flight.getSource()+" to "+flight.getDestination());
		System.out.println("Date: "+date+" Rs."+flight.getPrice());
		System.out.println("----------------------------------------");
		
		
	}
}
