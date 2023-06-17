package com.shopwell.api.model.VOs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartResponseVO {

    private String message;

    private Long cartId;
}
