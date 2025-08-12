package com.yogesh.flightbookingsystem;

public class Flight {
	private String flightNo;
	private String source;
	private String destination;
	private double price;
	
	public Flight(String flightNo, String source, String destination, double price) {
		this.flightNo = flightNo;
		this.source = source;
		this.destination = destination;
		this.price = price;
		
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
