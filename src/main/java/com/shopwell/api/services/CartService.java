package com.shopwell.api.services;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.AddToCartRequestVO;
import com.shopwell.api.model.VOs.response.CartItemResponseVO;

import java.util.List;

public interface CartService {

    String addProductToCart(AddToCartRequestVO addToCartRequestVO) throws CustomerNotFoundException;

    void removeProductFromCart(Long productId) throws CustomerNotFoundException;

    List<CartItemResponseVO> getCartItems() throws CustomerNotFoundException;

    Double calculateTotalPrice(String email);
}
