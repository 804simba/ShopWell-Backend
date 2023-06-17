package com.shopwell.api.model.VOs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequestVO {

    private Long customerId;

    private Long productId;

    private int quantity;
}
