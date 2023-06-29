package com.shopwell.api.model.VOs.response;

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

    private String productPrice;

    private int quantity;

    private String productImage;
}
