package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.VendorRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.service.VendorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vendors")
@Slf4j
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    public ResponseEntity<ApiResponseVO<String>> registerVendor(@RequestBody VendorRegistrationVO vendorRegistrationVO) {
        log.info("Registering Customer: " + vendorRegistrationVO);
        vendorService.registerVendor(vendorRegistrationVO);

        return ResponseEntity.ok(ApiResponseVO.<String>builder()
                .message("Vendor saved successfully")
                .build());
    }
}
