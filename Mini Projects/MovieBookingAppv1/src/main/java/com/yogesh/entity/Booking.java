package com.yogesh.entity;

import java.sql.Timestamp;

public class Booking {
    private Long bookingId;
    private Long userId;
    public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getShowId() {
		return showId;
	}
	public void setShowId(Long showId) {
		this.showId = showId;
	}
	public Timestamp getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(Timestamp bookingTime) {
		this.bookingTime = bookingTime;
	}
	public boolean isCancellationStatus() {
		return cancellationStatus;
	}
	public void setCancellationStatus(boolean cancellationStatus) {
		this.cancellationStatus = cancellationStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	private Long showId;
    private Timestamp bookingTime;
    private boolean cancellationStatus;
    private String paymentStatus;

    // getters and setters
}
