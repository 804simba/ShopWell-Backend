package com.shopwell.api.model.entity;

import jakarta.persistence.*;
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
        name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(name = "emailId_unique", columnNames = "employee_email"),
                @UniqueConstraint(name = "guardianMobile_unique", columnNames = "employee_phone_number")
        }
)
public class Employee {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_first_name")
    private String employeeFirstName;

    @Column(name = "employee_last_name")
    private String employeeLastName;

    @Column(name = "employee_email")
    private String employeeEmail;

    @Temporal(TemporalType.DATE)
    @Column(name = "employee_dob")
    private Timestamp employeeDateOfBirth;

    @Column(name = "employee_phone_number")
    private String employeePhoneNumber;

    @Column(name = "employee_street_address")
    private String employeeStreetAddress;

    @Column(name = "employee_city")
    private String employeeCity;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}
