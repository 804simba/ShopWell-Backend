package com.shopwell.api.services.implementations;

import com.shopwell.api.events.RegisterEvent;
import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.response.ResponseOTPVO;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.OTPConfirmation;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.repository.OTPRepository;
import com.shopwell.api.services.OTPService;
import com.shopwell.api.utils.RandomValues;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerOTPServiceImpl implements OTPService<Customer> {
    private final OTPRepository otpRepository;
    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public String generateOTP(Customer customer)  {
        String otp = RandomValues.generateRandom();
        OTPConfirmation confirmationToken = new OTPConfirmation(otp, customer);
        sendOtp(customer,otp,confirmationToken);
        return otp;
    }

    @Override
    @SneakyThrows
    public ResponseOTPVO verifyUserOtp(String email, String otp) {
        Customer customer = customerRepository.findCustomerByCustomerEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        log.info("Verifying OTP:: " + customer);
        OTPConfirmation otpConfirmation = otpRepository.findByCustomerAndOtp_generator(customer.getId(), otp);
        System.out.println(otpConfirmation);
        if (otpConfirmation != null && !isOtpExpired(otpConfirmation)) {
            System.out.println(otpConfirmation.getCustomer());
            otpConfirmation.getCustomer().setStatus(true);
            customerRepository.save(otpConfirmation.getCustomer());
            return ResponseOTPVO.builder()
                    .message(otpConfirmation.getCustomer().getEmail())
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
        OTPConfirmation otpConfirmation = otpRepository.findId(customer.getId());
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
