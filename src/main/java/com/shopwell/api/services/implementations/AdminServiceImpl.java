package com.shopwell.api.services.implementations;

import com.shopwell.api.model.VOs.request.AdminRegistrationRequest;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.entity.AdminUser;
import com.shopwell.api.repository.AdminRepository;
import com.shopwell.api.services.AdminService;
import com.shopwell.api.services.image.UserImageService;
import com.shopwell.api.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final MapperUtils mapperUtils;

    private final PasswordEncoder passwordEncoder;

    private final UserImageService userImageService;

    @Override
    public ApiResponseVO<String> registerAdmin(AdminRegistrationRequest adminRegistrationRequest) {
        AdminUser admin = mapperUtils.adminVOToAdminEntity(adminRegistrationRequest);
        admin.setAdminPassword(passwordEncoder.encode(adminRegistrationRequest.getAdminPassword()));
        Random random = new Random();
        Long imageId = random.nextLong(9000) + 1000;

        String imageURL = userImageService.uploadImage(imageId, adminRegistrationRequest.getAdminImage());
        admin.setAdminImageURL(imageURL);
        adminRepository.save(admin);
        return ApiResponseVO.<String>builder().message("Admin account created").build();
    }
}
