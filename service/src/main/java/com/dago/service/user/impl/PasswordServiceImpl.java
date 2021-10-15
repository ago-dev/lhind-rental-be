package com.dago.service.user.impl;


import com.dago.domain.PasswordResetToken;
import com.dago.domain.User;
import com.dago.persistence.PasswordResetTokenRepository;
import com.dago.persistence.UserRepository;
import com.dago.service.LoggedUser;
import com.dago.service.exception.InvalidTokenException;
import com.dago.service.exception.ResourceNotFoundException;
import com.dago.service.user.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void requestResetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        PasswordResetToken passwordResetToken = passwordResetTokenRepository
                .save(new PasswordResetToken(UUID.randomUUID().toString(), user));

        mailSender.send(constructResetTokenEmail(passwordResetToken.getToken(), user));
    }

    @Override
    public void changePassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("Token is invalid!"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Token is expired!");
        }

        if (resetToken.getUser() != null &&
                !resetToken.getUser().getUsername().equals(retrieveCurrentUserEntity().getUsername())) {
            User user = resetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            passwordResetTokenRepository.delete(resetToken);
            return;
        }

        throw new ResourceNotFoundException("User could not be found!");
    }

    private SimpleMailMessage constructResetTokenEmail(String token, User user) {
        String message = "You've requested to change your account passowrd in Lhind-rental. \n" +
                "Use this token to change your password: " + token + "\n"
                + "WARNING: Contact us if it was not you requesting this action!";

        return constructEmail("Reset Password", message + "\n", user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());

        return email;
    }

    private User retrieveCurrentUserEntity() {
        return userRepository.findByUsername(LoggedUser.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("User not found!")
        );
    }
}