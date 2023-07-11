package com.shopwell.api.repository;

import com.shopwell.api.model.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRepository extends JpaRepository<OTP,Long> {
    OTP findByUser_EmailAndOtp(String email, String otp);

    OTP findByUserId(Long id);
}
