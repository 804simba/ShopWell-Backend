package com.shopwell.api.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    PENDING("Pending"),

    PROCESSING("Processing"),

    SHIPPED("Shipped"),

    DELIVERED("Delivered"),

    CANCELLED("Cancelled");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
