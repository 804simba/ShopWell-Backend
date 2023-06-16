package com.shopwell.api.repository;

import com.shopwell.api.model.entity.ProductVendor;
import com.shopwell.api.model.entity.ProductVendorKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVendorRepository extends JpaRepository<ProductVendor, ProductVendorKey> {
}
