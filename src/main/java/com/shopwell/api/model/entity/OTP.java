package com.shopwell.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="otp_verification")
@Getter
@Setter
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnoreProperties("otpConfirmations")
    private BaseUser user;

    public OTP(String otp, BaseUser user) {
        this.otp = otp;
        this.user = user;
        this.expiresAt = calculateExpirationDate();
    }

    private LocalDateTime calculateExpirationDate() {
        return LocalDateTime.now().plusMinutes(5);
    }
}
