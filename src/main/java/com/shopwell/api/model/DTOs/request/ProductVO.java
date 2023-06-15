package com.shopwell.api.model.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductVO {
    private String productName;

    private Long productPrice;

    private String productDescription;

    private String brandName;
}
