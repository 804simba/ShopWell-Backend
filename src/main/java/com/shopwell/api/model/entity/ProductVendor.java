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
    @EmbeddedId
    private ProductVendorKey productVendorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("vendorId")
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
