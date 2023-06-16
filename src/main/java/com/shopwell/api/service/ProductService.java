package com.shopwell.api.service;

import com.shopwell.api.exceptions.ProductNotFoundException;
import com.shopwell.api.model.DTOs.request.ProductRegistrationVO;
import com.shopwell.api.model.DTOs.response.ApiResponse;

import java.util.List;

public interface ProductService {
    ApiResponse<?> saveProduct(ProductRegistrationVO productRegistrationVO);

    ApiResponse<?> editProduct(Long id, ProductRegistrationVO productRegistrationVO) throws ProductNotFoundException;

    ApiResponse<?> getProduct(Long id) throws ProductNotFoundException;

    ApiResponse<List<ProductRegistrationVO>> searchProducts(String keyword);

    ApiResponse<?> deleteProduct(Long id);
}
