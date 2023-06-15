package com.shopwell.api.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVendor {
    private Long productNumber;

    private Long vendorId;

    private BigDecimal wholeSalePrice;

    private List<String> daysToDeliver;
}
