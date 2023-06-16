package com.shopwell.api.service.implementations;

import com.shopwell.api.model.DTOs.request.VendorRegistrationVO;
import com.shopwell.api.model.entity.Vendor;
import com.shopwell.api.repository.VendorRepository;
import com.shopwell.api.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    @Override
    public void registerVendor(VendorRegistrationVO registrationVO) {
        vendorRepository.save(mapVendorRegistrationVO(registrationVO));
    }

    private Vendor mapVendorRegistrationVO(VendorRegistrationVO registration) {
        return Vendor.builder()
                .vendorName(registration.getVendorName())
                .vendorEmailAddress(registration.getVendorEmailAddress())
                .vendorPhoneNumber(registration.getVendorPhoneNumber())
                .vendorCity(registration.getVendorCity())
                .vendorStreetAddress(registration.getVendorStreetAddress())
                .vendorWebsiteAddress(registration.getVendorWebsiteAddress())
                .vendorZipCode(registration.getVendorZipCode())
                .build();
    }
}
