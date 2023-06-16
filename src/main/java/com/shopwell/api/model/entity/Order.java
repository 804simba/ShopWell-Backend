package com.shopwell.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "order_id")
    private Long orderId;

    @Temporal(TemporalType.DATE)
    @Column(name = "order_date")
    private Timestamp orderDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "order_ship_date")
    private Timestamp shipDate;

    @Column(name = "order_total")
    private BigDecimal orderTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
    private Employee employee;
}
