package com.yogesh.flightbookingsystem;

import java.util.Scanner;
import java.util.ArrayList;

public class FlightBookingApp {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Admin admin = new Admin("Aditya");
		
		ArrayList<Passenger> passengers = new ArrayList<>();
		ArrayList<Flight> flights = new ArrayList<>();
		ArrayList<Booking> bookings = new ArrayList<>();	
		
		flights.add(new Flight("AIR101", "Chennai", "Delhi", 3500));
		flights.add(new Flight("EK505", "Mumbai", "Dubai", 8500));
		flights.add(new Flight("ING202", "Pune", "Kolkata", 4500));
		
		int passengerIdCounter = 1;
		
		while(true) {
			admin.showMenu();
			System.out.println("Enter you choice: ");
			int choice = sc.nextInt();
			sc.nextLine();
			switch(choice) {
			case 1:
				System.out.println("Enter Passanger Name: ");
				String pname = sc.nextLine();
				System.out.println("Enter Contact: ");
				String contact = sc.nextLine();
				
				passengers.add(new Passenger(passengerIdCounter++, pname, contact));
				System.out.println("Passenger Details added successfully");
				break;
			case 2:
				System.out.println("Available Flights: ");
				for(Flight f: flights) {
					System.out.println(f.getFlightNo()
							+": "+f.getSource()
							+"-> "+f.getDestination()
							+"- Rs."+ f.getPrice());
				}
				break;
			case 3:
				if(passengers.isEmpty()) {
					System.out.println("Register a Passenger First");
					break;
				}
				System.out.println("Passenger List");
				for(Passenger p: passengers) {
					System.out.println(p.getId()+": "+p.getName());
				}
				System.out.println("Enter Passenger ID:");
				int pid = sc.nextInt();
				sc.nextLine();
				
				Passenger selectedPassenger = null;
				for(Passenger p: passengers) {
					if(p.getId()==pid) {
						selectedPassenger = p;
						break;
					}
				}
				if(selectedPassenger == null) {
					System.out.println("Invalid ID");
					break;
				}
				System.out.println("Flights");
				for(int i=0; i<flights.size(); i++) {
					System.out.println((i+1)+". "+flights.get(i).getFlightNo());
				}
				System.out.println("Choose Flight Number: ");
				int fid = sc.nextInt();
				sc.nextLine();
				
				System.out.println("Enter Travel Date(dd-mm-yyy): ");
				String date = sc.nextLine();
				
				bookings.add(new Booking(selectedPassenger, flights.get(fid-1),date));
				System.out.println("Flight Booked");
				break;
			case 4:
				
				if(bookings.isEmpty()) {
					System.out.println("No Bookings Yet");
					break;
				}
				for(Booking b:bookings) {
					b.display();
				}
				break;
			case 5:
				System.out.println("Enter Source: ");
				String src = sc.nextLine().toLowerCase();
				System.out.println("Enter Destination: ");
				String dest = sc.nextLine().toLowerCase();
				
				boolean found = false;
				System.out.println("Search Results: ");
				for(Flight f:flights) {
					if(f.getSource().toLowerCase().equals(src) && f.getDestination().toLowerCase().equals(dest)) {
						System.out.println(f.getFlightNo()+": "+
					f.getSource()+"-> "+f.getDestination()+" - "+f.getPrice() );
						found = true;
						
					}
				}
				if(!found) {
					System.out.println("No Flights found for the selected Source and Destination");
				}
				
				break;
			case 6:
				System.out.println("Exiting the Application");
				sc.close();
				System.exit(0);
				break;
				
			default:
				System.out.println("Invalid Choice");
				break;
			}
		}
	}

}
