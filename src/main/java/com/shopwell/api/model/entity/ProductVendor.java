package com.shopwell.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVendor implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendorId")
    private Vendor vendor;

    private BigDecimal wholeSalePrice;

    @ElementCollection
    @CollectionTable(name = "delivery_days", joinColumns = {
            @JoinColumn(name = "product_id", referencedColumnName = "productId"),
            @JoinColumn(name = "vendor_id", referencedColumnName = "vendorId")
    })
    private List<String> daysToDeliver;
}
