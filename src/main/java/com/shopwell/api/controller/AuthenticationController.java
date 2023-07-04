package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.CustomerLoginRequestVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.VOs.response.ResponseOTPVO;
import com.shopwell.api.service.AuthService;
import com.shopwell.api.service.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication")
@SecurityRequirement(name = "Bearer Authentication")
public class AuthenticationController {
    private final OtpService otpService;
    private final AuthService authService;

    @Operation(
            summary = "This is an endpoint for user login authentication.",
            responses = {
                    @ApiResponse(description = "User authenticated", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    @SneakyThrows
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponseVO<CustomerResponseVO>> loginAuth(@RequestBody CustomerLoginRequestVO customerLoginRequestVO) {
        log.info("Getting Customer by email: " + customerLoginRequestVO);
        CustomerResponseVO customer = authService.authenticate(customerLoginRequestVO);
        ApiResponseVO<CustomerResponseVO> apiResponseVO =new ApiResponseVO<>(customer);
        return new ResponseEntity<>(apiResponseVO,HttpStatus.OK);

    }

    @Operation(
            summary = "This is an endpoint for verifying user OTP.",
            responses = {
                    @ApiResponse(description = "OTP verified successfully", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    @SneakyThrows
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponseVO<ResponseOTPVO>> verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String otp) {
        ApiResponseVO<ResponseOTPVO> apiResponseVO = new ApiResponseVO<>(otpService.verifyUserOtp(email, otp));
        return new ResponseEntity<>(apiResponseVO, HttpStatus.OK);
    }

    @Operation(
            summary = "This is an endpoint for resending OTP to user.",
            responses = {
                    @ApiResponse(description = "OTP resent successfully", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    @SneakyThrows
    @GetMapping("/resend-otp")
    public ResponseEntity<ApiResponseVO<ResponseOTPVO>> resendOTP(@RequestParam("email") String email) {
        ApiResponseVO<ResponseOTPVO> apiResponseVO = new ApiResponseVO<>(otpService.resendOtp(email));
        return new ResponseEntity<>(apiResponseVO, HttpStatus.OK);
    }
}
