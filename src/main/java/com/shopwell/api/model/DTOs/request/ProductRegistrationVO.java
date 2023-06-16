package com.shopwell.api.model.DTOs.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductRegistrationVO {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 20)
    private String productName;

    @NotEmpty(message = "Enter product description")
    private String productDescription;

    @NotNull
    @NotEmpty(message = "Enter product price")
    private Long productPrice;

    @NotEmpty(message = "Enter brand name")
    private String brandName;

    @NotEmpty(message = "Enter quantity of product available")
    private Integer quantityAvailable;
}
