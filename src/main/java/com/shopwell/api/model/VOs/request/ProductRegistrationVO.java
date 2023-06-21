package com.shopwell.api.model.VOs.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

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
    private BigDecimal productPrice;

    @NotEmpty(message = "Enter brand name")
    private String brandName;

    @NotEmpty(message = "Enter category name")
    private String categoryName;

    @NotEmpty(message = "Enter quantity of product available")
    private Integer quantityAvailable;

    private List<MultipartFile> imageFiles;
}
