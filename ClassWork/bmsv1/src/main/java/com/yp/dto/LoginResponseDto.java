package com.yp.dto;


public class LoginResponseDto {
    private boolean isLoggedIn;
    private ResponseStatus status;

    // getters and setters
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
    public ResponseStatus getStatus() {
        return status;
    }
    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}
