package com.shopwell.api.services;

import com.shopwell.api.model.VOs.response.ResponseOTPVO;
import com.shopwell.api.model.entity.Customer;

public interface OtpService {
    String generateOTP(Customer customer);
    ResponseOTPVO verifyUserOtp(String email,String otp);
    ResponseOTPVO resendOtp(String email);
}
