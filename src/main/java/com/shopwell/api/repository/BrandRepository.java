package com.shopwell.api.repository;

import com.shopwell.api.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByBrandName(String nike);
}
