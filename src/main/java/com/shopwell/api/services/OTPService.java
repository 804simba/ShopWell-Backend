package com.shopwell.api.services;

import com.shopwell.api.model.VOs.response.ResponseOTPVO;
import com.shopwell.api.model.entity.BaseUser;
import com.shopwell.api.model.entity.OTP;

public interface OTPService<T extends BaseUser> {
    void saveOTP(OTP otp);

    ResponseOTPVO verifyUserOtp(String email, String otp);

    ResponseOTPVO resendOtp(String email);

    void sendOtp(T user, String otp, OTP newToken);

    OTP generateOTP(BaseUser user);
}

