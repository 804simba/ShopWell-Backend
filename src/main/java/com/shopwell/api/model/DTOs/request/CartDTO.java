package com.shopwell.api.model.DTOs.request;

import com.shopwell.api.model.DTOs.request.CartRequestVO.CartItemVO;

import java.util.List;

public class CartDTO {

    private Long cartId;

    private List<CartItemVO> items;
}
