package com.shopwell.api.service;

import com.shopwell.api.model.VOs.response.ResponseOTPVO;

public interface OtpService {
    ResponseOTPVO verifyUserOtp(String email,String otp);
    ResponseOTPVO resendOtp(String email);
}
