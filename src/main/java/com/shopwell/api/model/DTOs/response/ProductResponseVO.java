package com.shopwell.api.model.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseVO {
    private Long productId;

    private String productName;

    private List<String> productImageURLs;

    private String productPrice;

    private Integer quantityAvailable;
}
