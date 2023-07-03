package com.shopwell.api.repository;

import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.OTPConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTPConfirmation,Long> {
    @Query(value = "SELECT * FROM otp_verification WHERE customer_id=?1",nativeQuery = true)
    OTPConfirmation findId(Long user_id);
    @Query(value = "SELECT * FROM otp_verification WHERE customer_id=?1 and otp_generator=?2",nativeQuery = true)
    OTPConfirmation findByCustomerAndOtp_generator(Long id,String otp_generator);
}
