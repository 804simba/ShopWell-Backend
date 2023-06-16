package com.shopwell.api.model.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItemVO {

    private Long productId;

    private String productName;

    private BigDecimal price;

    private int quantity;
}
