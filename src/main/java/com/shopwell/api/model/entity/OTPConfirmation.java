package com.shopwell.api.model.entity;

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
public class OTPConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String otp_generator;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @OneToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;
    public OTPConfirmation(String otp_generator, Customer customer) {
        this.otp_generator = otp_generator;
        this.customer = customer;
        this.expiresAt = calculateExpirationDate();
    }
    private LocalDateTime calculateExpirationDate() {
        return LocalDateTime.now().plusMinutes(5);
    }

}
