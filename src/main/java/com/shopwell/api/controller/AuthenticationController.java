package com.shopwell.api.controller;

import com.cloudinary.Api;
import com.shopwell.api.model.VOs.request.CustomerEmailRequestVO;
import com.shopwell.api.model.VOs.request.CustomerLoginRequestVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.VOs.response.ResponseOTPVO;
import com.shopwell.api.service.AuthService;
import com.shopwell.api.service.CustomerService;
import com.shopwell.api.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final CustomerService customerService;
    private final OtpService otpService;
    private final AuthService authService;
    @SneakyThrows
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponseVO<CustomerResponseVO>> loginAuth(@RequestBody CustomerLoginRequestVO customerLoginRequestVO) {
        log.info("Getting Customer by email: " + customerLoginRequestVO);
        CustomerResponseVO customer = authService.authenticate(customerLoginRequestVO);
        ApiResponseVO<CustomerResponseVO> apiResponseVO =new ApiResponseVO<>(customer);
        return new ResponseEntity<>(apiResponseVO,HttpStatus.OK);

    }



    @SneakyThrows
    @GetMapping("/resend-otp")
    public ResponseEntity<ApiResponseVO<ResponseOTPVO>> resendOTP(@RequestParam("email") String email) {
        ApiResponseVO<ResponseOTPVO> apiResponseVO = new ApiResponseVO<>(otpService.resendOtp(email));
        return new ResponseEntity<>(apiResponseVO, HttpStatus.OK);
    }
}
