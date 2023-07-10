package com.shopwell.api.services.implementations;

import com.shopwell.api.events.UserRegistrationEvent;
import com.shopwell.api.model.VOs.request.AdminRegistrationRequest;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.entity.AdminUser;
import com.shopwell.api.model.entity.OTP;
import com.shopwell.api.repository.AdminRepository;
import com.shopwell.api.services.AdminService;
import com.shopwell.api.services.OTPService;
import com.shopwell.api.utils.MapperUtils;
import com.shopwell.api.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final MapperUtils mapperUtils;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher publisher;

    private final OTPService<AdminUser> otpService;

    @Override
    public ApiResponseVO<String> registerAdmin(AdminRegistrationRequest adminRegistrationRequest) {
        AdminUser admin = (AdminUser) mapperUtils.adminVOToAdminEntity(adminRegistrationRequest);
        admin.setPassword(passwordEncoder.encode(adminRegistrationRequest.getPassword()));
        admin.setImageURL(UserUtils.IMAGE_PLACEHOLDER_URL);
        adminRepository.save(admin);

        OTP otpEntity = otpService.generateOTP(admin);
        String otp = otpEntity.getOtp();
        publisher.publishEvent(new UserRegistrationEvent(admin, otp));

        LocalDateTime time = LocalDateTime.now();
        return ApiResponseVO.<String>builder()
                .time(time.toString())
                .message("Admin account created")
                .payload(admin.getEmail()).build();
    }
}
