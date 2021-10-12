package com.dago.service.user.enums;

public enum UserRole {
    ADMIN("Admin"), MEMBER("Member");

    private String value;

    private UserRole(String value) {
        this.value = value;
    }

    public String returnValue() {
        return this.value;
    }
}
