package com.dago.service.exception;

public class UserNotAuthorizedException extends RuntimeException {
    public static final String UNAUTHORIZED = "User is not authorized for this operation!";

    public UserNotAuthorizedException() {
        super(UNAUTHORIZED);
    }
}