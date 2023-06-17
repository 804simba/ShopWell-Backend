package com.shopwell.api.model.VOs.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartRequestVO {

    private Long cartId;

    private List<CartItemVO> items;
}
