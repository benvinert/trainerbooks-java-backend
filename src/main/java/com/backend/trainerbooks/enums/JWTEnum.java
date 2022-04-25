package com.backend.trainerbooks.enums;

public enum JWTEnum {
    BEARER("Bearer "),
    AUTHORIZATION("Authorization");

    JWTEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
