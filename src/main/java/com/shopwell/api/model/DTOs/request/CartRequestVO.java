package com.shopwell.api.model.DTOs.request;

import java.math.BigDecimal;
import java.util.List;

public class CartRequestVO {

    private Long cartId;
    private List<CartItemVO> items;

    public static class CartItemVO {
        private Long productId;

        private String productName;

        private BigDecimal price;

        private int quantity;
    }
}
