package com.shopwell.api.services.implementations;

import com.shopwell.api.events.UserRegistrationEvent;
import com.shopwell.api.exceptions.OTPException;
import com.shopwell.api.model.VOs.response.ResponseOTPVO;
import com.shopwell.api.model.entity.AdminUser;
import com.shopwell.api.model.entity.BaseUser;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.OTP;
import com.shopwell.api.repository.AdminRepository;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.repository.OTPRepository;
import com.shopwell.api.services.OTPService;
import com.shopwell.api.utils.RandomValues;
import com.shopwell.api.utils.VerifyUserUtil;
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
public class OTPServiceImpl<T extends BaseUser> implements OTPService<T> {

    private final OTPRepository otpRepository;

    private final CustomerRepository customerRepository;

    private final AdminRepository adminRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final VerifyUserUtil verifyUserUtil;

    @Override
    public void saveOTP(OTP otp) {
        otpRepository.save(otp);
    }

    @Override
    @SneakyThrows
    public ResponseOTPVO verifyUserOtp(String email, String otp) {
        BaseUser user = verifyUserUtil.verifyIfCustomerOrAdminEmail(email);

        log.info("Verifying OTP:: " + user.getEmail());
        OTP otpConfirmation = otpRepository.findByUser_EmailAndOtp(user.getEmail(), otp);
        System.out.println(otpConfirmation);

        if (otpConfirmation != null && !isOtpExpired(otpConfirmation)) {
            System.out.println(otpConfirmation.getUser());
            user.setStatus(true);
            if (user instanceof Customer customer) {
                customerRepository.save(customer);
            } else {
                AdminUser admin = (AdminUser) user;
                adminRepository.save(admin);
            }
            return ResponseOTPVO.builder()
                    .message(user.getEmail())
                    .localDateTime(LocalDateTime.now())
                    .build();
        } else {
            return ResponseOTPVO.builder()
                    .message("Invalid or expired OTP")
                    .localDateTime(LocalDateTime.now())
                    .build();
        }
    }
    public void sendOtp(BaseUser user, String otp, OTP newOTP){
        OTP foundOTP = otpRepository.findByUserId(user.getId());

        if (foundOTP != null){
            otpRepository.delete(foundOTP);
        }
        otpRepository.save(newOTP);
        System.out.println(otp);
        applicationEventPublisher.publishEvent(new UserRegistrationEvent(user, otp));
    }

    @Override
    public ResponseOTPVO resendOtp(String email) {
        BaseUser user;
        try {
            user = verifyUserUtil.verifyIfCustomerOrAdminEmail(email);

            String generatedOTP = RandomValues.generateRandom();
            OTP newOTP = generateOTP(user);

            sendOtp(user, generatedOTP, newOTP);
            return ResponseOTPVO.builder()
                    .message(generatedOTP)
                    .localDateTime(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            log.info("Error resending OTP: {}", e.getMessage());
            throw new OTPException("Error resending OTP");
        }
    }
    private boolean isOtpExpired(OTP otp) {
        LocalDateTime otpCreationTime = otp.getExpiresAt();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(otpCreationTime, currentDateTime);
        long minutesPassed = duration.toMinutes();
        System.out.println(minutesPassed);
        long otpExpirationMinutes = 4;
        return minutesPassed > otpExpirationMinutes;
    }

    @Override
    public OTP generateOTP(BaseUser user)  {
        String otp = RandomValues.generateRandom();

        OTP otpEntity;

        if (user instanceof Customer customer) {
            otpEntity = new OTP(otp, customer);
        } else if (user instanceof AdminUser adminUser) {
            otpEntity = new OTP(otp, adminUser);
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }

        return otpEntity;
    }
}