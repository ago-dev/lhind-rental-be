package com.dago.service.user;

public interface PasswordService {
    void requestResetPassword(String userEmail);
    void changePassword(String resetToken, String newPassword);
}