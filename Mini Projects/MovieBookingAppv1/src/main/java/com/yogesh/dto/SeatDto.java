package com.yogesh.dto;

public class SeatDto {

    public static class SeatResponse {
        private Long seatId;
        private String seatNumber;
        private String type;
        public Long getSeatId() {
			return seatId;
		}
		public void setSeatId(Long seatId) {
			this.seatId = seatId;
		}
		public String getSeatNumber() {
			return seatNumber;
		}
		public void setSeatNumber(String seatNumber) {
			this.seatNumber = seatNumber;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getPriceCategory() {
			return priceCategory;
		}
		public void setPriceCategory(String priceCategory) {
			this.priceCategory = priceCategory;
		}
		public boolean isAvailable() {
			return isAvailable;
		}
		public void setAvailable(boolean isAvailable) {
			this.isAvailable = isAvailable;
		}
		private String priceCategory;
        private boolean isAvailable;
        // getters and setters
    }
}
