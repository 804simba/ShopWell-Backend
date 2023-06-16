package com.shopwell.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "vendors")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "vendor_phone_number")
    private String vendorPhoneNumber;

    @Column(name = "vendor_street_address")
    private String vendorStreetAddress;

    @Column(name = "vendor_city")
    private String vendorCity;

    @Column(name = "vendor_state")
    private String vendorState;

    @Column(name = "vendor_zipcode")
    private String vendorZipCode;

    @Column(name = "vendor_email")
    private String vendorEmailAddress;

    @Column(name = "vendor_website")
    private String vendorWebsiteAddress;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Product> vendorProducts;
}
