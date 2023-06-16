package com.shopwell.api.model.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItemResponseVO {

    private Long cartItemId;

    private Long productId;

    private String productName;

    private BigDecimal productPrice;

    private int quantity;

    private String productImage;
}
