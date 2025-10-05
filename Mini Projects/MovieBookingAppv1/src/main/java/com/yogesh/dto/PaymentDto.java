package com.yogesh.dto;

import java.math.BigDecimal;

public class PaymentDto {

    public static class PaymentRequest {
        private Long bookingId;
        private BigDecimal amount;
        public Long getBookingId() {
			return bookingId;
		}
		public void setBookingId(Long bookingId) {
			this.bookingId = bookingId;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		public String getPaymentMethod() {
			return paymentMethod;
		}
		public void setPaymentMethod(String paymentMethod) {
			this.paymentMethod = paymentMethod;
		}
		private String paymentMethod;
        // getters and setters
    }

    public static class PaymentResponse {
        private Long paymentId;
        private Long bookingId;
        private BigDecimal amount;
        private String transactionId;
        public Long getPaymentId() {
			return paymentId;
		}
		public void setPaymentId(Long paymentId) {
			this.paymentId = paymentId;
		}
		public Long getBookingId() {
			return bookingId;
		}
		public void setBookingId(Long bookingId) {
			this.bookingId = bookingId;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		public String getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}
		public String getPaymentMethod() {
			return paymentMethod;
		}
		public void setPaymentMethod(String paymentMethod) {
			this.paymentMethod = paymentMethod;
		}
		public String getGatewayResponse() {
			return gatewayResponse;
		}
		public void setGatewayResponse(String gatewayResponse) {
			this.gatewayResponse = gatewayResponse;
		}
		private String paymentMethod;
        private String gatewayResponse;
        // getters and setters
    }
}
