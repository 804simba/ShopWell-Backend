package com.shopwell.api.model.VOs.request;

import com.shopwell.api.model.VOs.request.CartRequestVO.CartItemVO;

import java.util.List;

public class CartDTO {

    private Long cartId;

    private List<CartItemVO> items;
}
