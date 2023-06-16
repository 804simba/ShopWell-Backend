package com.shopwell.api.utils;

import com.shopwell.api.model.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecification {
    public static Specification<Product> hasBrand(String brand) {

    }

    public static Specification<Product> hasName(String name) {

    }

    public static Specification<Product> hasPrice(Long price) {

    }
}
