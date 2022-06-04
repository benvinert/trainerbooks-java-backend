package com.backend.trainerbooks.enums;

public enum StatusTrainRequest {
    ACCEPT("accept"),
    PENDING("pending"),
    DENIED("denied");

    private String status;

    StatusTrainRequest(String role) {
        this.status = role;
    }

    public String getStatus() {
        return status;
    }
}
