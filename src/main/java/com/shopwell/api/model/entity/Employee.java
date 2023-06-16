package com.shopwell.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long employeeId;

    private String employeeFirstName;

    private String employeeLastName;

    private String employeeEmail;

    @Temporal(TemporalType.DATE)
    private Timestamp employeeDateOfBirth;

    private String employeePhoneNumber;

    private String employeeStreetAddress;

    private String employeeCity;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}
