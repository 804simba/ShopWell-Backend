package com.shopwell.api.service;

import com.shopwell.api.exceptions.ProductNotFoundException;
import com.shopwell.api.model.DTOs.request.OrderRequestVO;
import com.shopwell.api.model.DTOs.request.ProductRegistrationVO;
import com.shopwell.api.model.DTOs.response.ApiResponseVO;
import com.shopwell.api.model.entity.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ApiResponseVO<?> saveProduct(ProductRegistrationVO productRegistrationVO);

    ApiResponseVO<?> editProduct(Long id, ProductRegistrationVO productRegistrationVO) throws ProductNotFoundException;

    ApiResponseVO<?> getProduct(Long id) throws ProductNotFoundException;

    ApiResponseVO<List<ProductRegistrationVO>> searchProducts(String keyword);

    ApiResponseVO<?> deleteProduct(Long id);

    String addProductToCart(Long productId, Long customerId);

    String removeProductFromCart(Long productId, Long customerId);

    List<CartItem> getCartItems(Long customerId);

    BigDecimal calculateTotalPrice(Long customerId);
}
