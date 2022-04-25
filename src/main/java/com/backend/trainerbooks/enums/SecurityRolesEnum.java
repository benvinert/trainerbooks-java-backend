package com.backend.trainerbooks.enums;

public enum SecurityRolesEnum {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");


    private String role;

    SecurityRolesEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }




}
