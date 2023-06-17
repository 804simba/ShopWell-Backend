package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.BrandRegistrationVO;
import com.shopwell.api.model.VOs.request.CategoryRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponseVO<String>> registerBrand(@RequestBody final CategoryRegistrationVO categoryRegistrationVO) {

        categoryService.registerCategory(categoryRegistrationVO);

        return ResponseEntity.ok(ApiResponseVO.<String>builder()
                .message("Category saved successfully")
                .build());
    }
}
