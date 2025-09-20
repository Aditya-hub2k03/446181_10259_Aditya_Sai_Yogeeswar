package com.yogesh.flightbookingsystem;

import java.util.Scanner;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class FlightBookingApp {
    public static void main(String[] args) {
        // Show today's date at the start 
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String todayStr = sdf.format(new Date());
        System.out.println("======================================"); 
        System.out.println("   Flight Booking System");
        System.out.println("   Today's Date: " + todayStr);
        System.out.println("======================================\n");

        Scanner sc = new Scanner(System.in);
        Admin admin = new Admin("Aditya");

        ArrayList<Passenger> passengers = new ArrayList<>();
        ArrayList<Flight> flights = new ArrayList<>();
        ArrayList<Booking> bookings = new ArrayList<>();

        flights.add(new Flight("AIR101", "Chennai", "Delhi", 3500));
        flights.add(new Flight("EK505", "Mumbai", "Dubai", 8500));
        flights.add(new Flight("ING202", "Pune", "Kolkata", 4500));

        int passengerIdCounter = 1;

        while (true) {
            admin.showMenu();
            System.out.println("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter Passenger Name: ");
                    String pname = sc.nextLine();
                    System.out.println("Enter Contact: ");
                    String contact = sc.nextLine();

                    passengers.add(new Passenger(passengerIdCounter++, pname, contact));
                    System.out.println("Passenger Details added successfully");
                    break;

                case 2:
                    System.out.println("Available Flights: ");
                    for (Flight f : flights) {
                        System.out.println(f.getFlightNo() + ": " + f.getSource() + " -> " + f.getDestination() + " - Rs." + f.getPrice());
                    }
                    break;

                case 3:
                    if (passengers.isEmpty()) {
                        System.out.println("Register a Passenger First");
                        break;
                    }
                    System.out.println("Passenger List");
                    for (Passenger p : passengers) {
                        System.out.println(p.getId() + ": " + p.getName());
                    }
                    System.out.println("Enter Passenger ID:");
                    int pid = sc.nextInt();
                    sc.nextLine();

                    Passenger selectedPassenger = null;
                    for (Passenger p : passengers) {
                        if (p.getId() == pid) {
                            selectedPassenger = p;
                            break;
                        }
                    }
                    if (selectedPassenger == null) {
                        System.out.println("Invalid ID");
                        break;
                    }

                    System.out.println("Flights");
                    for (int i = 0; i < flights.size(); i++) {
                        System.out.println((i + 1) + ". " + flights.get(i).getFlightNo());
                    }
                    System.out.println("Choose Flight Number: ");
                    int fid = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter Travel Date (dd-MM-yyyy): ");
                    String dateStr = sc.nextLine();
                    sdf.setLenient(false); // Strict date parsing
                    Date travelDate = null;

                    try {
                        travelDate = sdf.parse(dateStr);

                        // Get today's date without time
                        Date today = sdf.parse(sdf.format(new Date()));

                        // Create max date (6 months from today)
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(today);
                        cal.add(Calendar.MONTH, 6);
                        Date maxDate = cal.getTime();

                        // Check past date
                        if (travelDate.before(today)) {
                            System.out.println("Error: Travel date cannot be in the past.");
                            break;
                        }

                        // Check date beyond 6 months
                        if (travelDate.after(maxDate)) {
                            System.out.println("Error: Travel date cannot be more than 6 months from today.");
                            break;
                        }

                    } catch (Exception e) {
                        System.out.println("Invalid Date Format! Booking cancelled.");
                        break;
                    }

                    bookings.add(new Booking(selectedPassenger, flights.get(fid - 1), travelDate));
                    System.out.println("Flight Booked");
                    break;

                case 4:
                    if (bookings.isEmpty()) {
                        System.out.println("No Bookings Yet");
                        break;
                    }
                    for (Booking b : bookings) {
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
                    for (Flight f : flights) {
                        if (f.getSource().toLowerCase().equals(src) && f.getDestination().toLowerCase().equals(dest)) {
                            System.out.println(f.getFlightNo() + ": " + f.getSource() + " -> " + f.getDestination() + " - " + f.getPrice());
                            found = true;
                        }
                    }
                    if (!found) {
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
