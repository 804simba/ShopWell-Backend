package com.shopwell.api.model.VOs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderItemVO {

    private Long productId;

    private String productPrice;

    private String quotedPrice;

    private String quantityOrdered;

    private String deliveryDate;
}
