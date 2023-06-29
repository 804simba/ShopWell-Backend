package com.shopwell.api.model.VOs.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ProductRegistrationVO implements Serializable {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 50)
    private String productName;

    @NotEmpty(message = "Enter product description")
    private String productDescription;

    @NotNull
    @NotEmpty(message = "Enter product price")
    private String productPrice;

    @NotEmpty(message = "Enter brand name")
    private String brandName;

    @NotEmpty(message = "Enter category name")
    private String categoryName;

    @NotEmpty(message = "Enter quantity of product available")
    private String quantityAvailable;

    @Size(min = 1, max = 10, message = "Product should have minimum 1 image and maximum 5 images")
    @JsonIgnore
    private List<MultipartFile> imageFiles;
}
