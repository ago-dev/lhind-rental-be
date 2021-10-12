package com.dago.service.user.enums;

public enum UserPrivilege {
    USER_READ("USER_READ"),
    USER_WRITE("USER_WRITE"),
    APPLICATION_READ("APPLICATION_READ"),
    APPLICATION_WRITE("APPLICATION_WRITE");

    private String value;

    private UserPrivilege(String value) {
        this.value = value;
    }

    public String returnValue() {
        return this.value;
    }
}
