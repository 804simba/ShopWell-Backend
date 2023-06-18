package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.BrandRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brand")
@Slf4j
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<ApiResponseVO<String>> registerBrand(@RequestBody BrandRegistrationVO brandRegistrationVO) {
        log.info(String.format("Registering brand: %s", brandRegistrationVO.toString()));
        brandService.registerBrand(brandRegistrationVO);

        return ResponseEntity.ok(ApiResponseVO.<String>builder()
                .message("Brand saved successfully")
                .build());
    }
}
