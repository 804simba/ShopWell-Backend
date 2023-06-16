package com.shopwell.api.service;

import com.shopwell.api.model.DTOs.request.VendorRegistrationVO;

public interface VendorService {

    void registerVendor(VendorRegistrationVO registrationVO);
}
