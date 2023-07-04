package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.CategoryRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@Slf4j
@Tag(name = "Category")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "This is an endpoint for registering a category.",
            responses = {
                    @ApiResponse(description = "Category created", responseCode = "201"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    @PostMapping
    public ResponseEntity<ApiResponseVO<String>> registerBrand(@RequestBody final CategoryRegistrationVO categoryRegistrationVO) {
        log.info(String.format("Registering Category %s: ", categoryRegistrationVO.toString()));
        categoryService.registerCategory(categoryRegistrationVO);

        return ResponseEntity.ok(ApiResponseVO.<String>builder()
                .message("Category saved successfully")
                .build());
    }
}
