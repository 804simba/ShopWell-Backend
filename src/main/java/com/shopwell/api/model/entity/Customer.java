package com.shopwell.api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "customers",
        uniqueConstraints = {
                @UniqueConstraint(name = "emailId_unique", columnNames = "customer_email"),
                @UniqueConstraint(name = "guardianMobile_unique", columnNames = "customer_phone_number")
        }
)
public class Customer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_first_name")
    private String customerFirstName;

    @Column(name = "customer_last_name")
    private String customerLastName;

    @Column(name = "customer_email")
    private String customerEmail;

    @Temporal(TemporalType.DATE)
    @Column(name = "customer_dob")
    private Timestamp customerDateOfBirth;

    @Column(name = "customer_phone_number")
    private String customerPhoneNumber;

    @Column(name = "customer_street_address")
    private String customerStreetAddress;

    @Column(name = "customer_city")
    private String customerCity;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}
