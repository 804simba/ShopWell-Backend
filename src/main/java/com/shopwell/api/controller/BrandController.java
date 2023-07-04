package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.BrandRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Brand")
@SecurityRequirement(name = "Bearer Authentication")
public class BrandController {

    private final BrandService brandService;

    @Operation(
            summary = "This is an endpoint for registering a brand.",
            responses = {
                    @ApiResponse(description = "Brand created", responseCode = "201"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    @PostMapping
    public ResponseEntity<ApiResponseVO<String>> registerBrand(@RequestBody BrandRegistrationVO brandRegistrationVO) {
        log.info(String.format("Registering brand: %s", brandRegistrationVO.toString()));
        brandService.registerBrand(brandRegistrationVO);

        return ResponseEntity.ok(ApiResponseVO.<String>builder()
                .message("Brand saved successfully")
                .build());
    }
}
