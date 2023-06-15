package com.shopwell.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long productNumber;

    private String productName;

    private Long productPrice;

    private String productDescription;

    private String brandName;
}
