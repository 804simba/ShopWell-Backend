package com.shopwell.api.service.implementations;

import com.shopwell.api.model.VOs.response.Response;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.OTPConfirmation;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.repository.OTPRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class OtpServiceImpl {
    private final OTPRepository otpRepository;
    private final CustomerRepository customerRepository;
    public OTPConfirmation findUser(Customer customer){
        return otpRepository.findId(customer.getCustomerId());
    }

    public void delete(OTPConfirmation confirmation){
        otpRepository.delete(confirmation);
    }
    public void saveOtp(OTPConfirmation confirmation){
        otpRepository.save(confirmation);
    }


    public Object verifyUserOtp(String email, String otp) {
        Customer customer = customerRepository.findCustomerByCustomerEmail(email).orElse(null);
        OTPConfirmation otpConfirmation = otpRepository.findByCustomerAndOtp_generator(customer.getCustomerId(), otp);
        if (otpConfirmation != null && !isOtpExpired(otpConfirmation)) {
            System.out.println(otpConfirmation.getCustomer());
            otpConfirmation.getCustomer().setCustomerStatus(true);
            customerRepository.save(otpConfirmation.getCustomer());
            return Response.builder()
                    .message(otpConfirmation.getCustomer().getCustomerEmail())
                    .localDateTime(LocalDateTime.now())
                    .build();
        } else {

            return Response.builder()
                    .message("Invalid or expired OTP")
                    .localDateTime(LocalDateTime.now())
                    .build();
        }
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
