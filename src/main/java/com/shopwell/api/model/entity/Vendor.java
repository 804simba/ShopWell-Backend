package com.shopwell.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long vendorId;

    private String vendorName;

    private String vendorPhoneNumber;

    private String vendorStreetAddress;

    private String vendorCity;

    private String vendorState;

    private String vendorZipCode;

    private String vendorEmailAddress;

    private String vendorWebsiteAddress;
}
