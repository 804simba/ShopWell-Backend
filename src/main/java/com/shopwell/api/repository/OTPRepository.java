package com.shopwell.api.repository;

import com.shopwell.api.model.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRepository extends JpaRepository<OTP,Long> {
    OTP findByUserIdAndOtp(Long id, String otp);

    OTP findByUserId(Long id);
}
