package com.shopwell.api.service;

import com.simba.shopwell.exceptions.ProductNotFoundException;
import com.simba.shopwell.model.DTOs.request.ProductVO;
import com.simba.shopwell.model.DTOs.response.ApiResponse;

import java.util.List;

public interface ProductService {
    ApiResponse<?> saveProduct(ProductVO productVO);

    ApiResponse<?> editProduct(Long id, ProductVO productVO) throws ProductNotFoundException;

    ApiResponse<?> getProduct(Long id) throws ProductNotFoundException;

    ApiResponse<List<ProductVO>> searchProducts(String keyword);

    ApiResponse<?> deleteProduct(Long id);
}
