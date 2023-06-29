package com.shopwell.api.service;

import com.shopwell.api.model.VOs.request.CartItemVO;
import com.shopwell.api.model.VOs.response.CartItemResponseVO;
import com.shopwell.api.model.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    String addProductToCart(Product product, Long customerId, int quantity);

    String removeProductFromCart(Product product, Long customerId);

    List<CartItemResponseVO> getCartItems(Long customerId);

    Double calculateTotalPrice(Long customerId);
}
