package com.shopwell.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class ProductVendorKey implements Serializable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "vendor_id")
    private Long vendorId;
}
