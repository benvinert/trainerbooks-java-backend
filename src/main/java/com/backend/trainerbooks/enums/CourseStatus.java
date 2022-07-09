package com.backend.trainerbooks.enums;

public enum CourseStatus {
    DRAFT(0),
    WAITING_FOR_REVIEW(1),
    DENIED(2),
    APPROVED(3);

    private int status;

    CourseStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}
