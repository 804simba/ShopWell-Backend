package com.shopwell.api.repository;

import com.shopwell.api.model.entity.ProductVendor;
import com.shopwell.api.model.entity.ProductVendorKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVendorRepository extends JpaRepository<ProductVendor, ProductVendorKey> {
}
