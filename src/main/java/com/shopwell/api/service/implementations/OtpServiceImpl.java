package com.shopwell.api.service.implementations;

import com.shopwell.api.event_driven.RegisterEvent;
import com.shopwell.api.model.VOs.response.ResponseOTPVO;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.OTPConfirmation;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.repository.OTPRepository;
import com.shopwell.api.service.OtpService;
import com.shopwell.api.utils.RandomValues;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class OtpServiceImpl  implements OtpService {
    private final OTPRepository otpRepository;
    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public ResponseOTPVO verifyUserOtp(String email, String otp) {
        Customer customer = customerRepository.findCustomerByCustomerEmail(email).orElse(null);
        System.out.println(customer);
        OTPConfirmation otpConfirmation = otpRepository.findByCustomerAndOtp_generator(customer.getCustomerId(), otp);
        System.out.println(otpConfirmation);
        if (otpConfirmation != null && !isOtpExpired(otpConfirmation)) {
            System.out.println(otpConfirmation.getCustomer());
            otpConfirmation.getCustomer().setCustomerStatus(true);
            customerRepository.save(otpConfirmation.getCustomer());
            return ResponseOTPVO.builder()
                    .message(otpConfirmation.getCustomer().getCustomerEmail())
                    .localDateTime(LocalDateTime.now())
                    .build();
        } else {

            return ResponseOTPVO.builder()
                    .message("Invalid or expired OTP")
                    .localDateTime(LocalDateTime.now())
                    .build();
        }
    }
    public void sendOtp(Customer customer,String otp,OTPConfirmation confirmationToken){
        System.out.println("****");
        OTPConfirmation otpConfirmation = otpRepository.findId(customer.getCustomerId());
        System.out.println("-----");
        if (otpConfirmation != null){
            otpRepository.delete(otpConfirmation);
        }
        otpRepository.save(confirmationToken);
        System.out.println(otp);
        applicationEventPublisher.publishEvent(new RegisterEvent(customer,otp));
    }

    @Override
    public ResponseOTPVO resendOtp(String email) {
        Customer customer = customerRepository.findCustomerByCustomerEmail(email).orElseThrow(()->new RuntimeException("USER NOT FOUND"));
        String otp = RandomValues.generateRandom();
        OTPConfirmation confirmationToken = new OTPConfirmation(otp, customer);
        sendOtp(customer,otp,confirmationToken);
        return ResponseOTPVO.builder()
                .message(otp)
                .localDateTime(LocalDateTime.now())
                .build();

    }
    private boolean isOtpExpired(OTPConfirmation otpConfirmation) {
        LocalDateTime otpCreationTime = otpConfirmation.getExpiresAt();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(otpCreationTime, currentDateTime);
        long minutesPassed = duration.toMinutes();
        System.out.println(minutesPassed);

        long otpExpirationMinutes = 4;

        return minutesPassed > otpExpirationMinutes;
    }

}
