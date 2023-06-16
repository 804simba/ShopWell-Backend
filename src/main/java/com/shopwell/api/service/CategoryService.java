package com.shopwell.api.service;

import com.shopwell.api.model.DTOs.request.CategoryRegistrationVO;

public interface CategoryService {
    void registerCategory(CategoryRegistrationVO categoryRegistrationVO);
}
