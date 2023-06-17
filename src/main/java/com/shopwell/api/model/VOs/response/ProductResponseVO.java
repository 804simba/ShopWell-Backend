package com.shopwell.api.model.VOs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseVO {
    private Long productId;

    private String productName;

    private String productDescription;

    private String brandName;

    private String categoryName;

    private List<String> productImageURLs;

    private String productPrice;

    private Integer quantityAvailable;
}
