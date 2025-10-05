package com.yogesh.dto;

import java.util.List;

public class BookingDto {

    public static class BookingRequest {
        private Long userId;
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
		public List<Long> getSeatIds() {
			return seatIds;
		}
		public void setSeatIds(List<Long> seatIds) {
			this.seatIds = seatIds;
		}
		private Long showId;
        private List<Long> seatIds;
        // getters and setters
    }

    public static class BookingResponse {
        private Long bookingId;
        private Long userId;
        private Long showId;
        private List<Long> seatIds;
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
		public List<Long> getSeatIds() {
			return seatIds;
		}
		public void setSeatIds(List<Long> seatIds) {
			this.seatIds = seatIds;
		}
		public String getBookingTime() {
			return bookingTime;
		}
		public void setBookingTime(String bookingTime) {
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
		private String bookingTime;
        private boolean cancellationStatus;
        private String paymentStatus;
        // getters and setters
    }
}
