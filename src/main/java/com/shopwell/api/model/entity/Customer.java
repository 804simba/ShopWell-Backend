package com.shopwell.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long customerId;

    private String customerFirstName;

    private String customerLastName;

    private String customerEmail;

    private String customerDateOfBirth;

    private String customerPhoneNumber;

    private String customerStreetAddress;

    private String customerCity;

    private String customerZipCode;
}
