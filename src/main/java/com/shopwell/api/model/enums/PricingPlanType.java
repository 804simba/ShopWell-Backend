package com.shopwell.api.model.enums;

public enum PricingPlanType {
    BASIC("Basic"),
    STANDARD("Standard"),
    PREMIUM("Premium");

    private final String value;

    PricingPlanType(String value) {
        this.value = value;
    }
}
