package com.dago.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "password_reset_token")
@Entity
public class PasswordResetToken extends BaseEntity<Integer>{
    private static final int EXPIRATION_HOURS = 24;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private LocalDateTime expiryDate;

    public PasswordResetToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusHours(EXPIRATION_HOURS);
    }
}
