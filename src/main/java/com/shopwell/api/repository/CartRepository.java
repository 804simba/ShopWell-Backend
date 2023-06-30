package com.shopwell.api.repository;

import com.shopwell.api.model.entity.Cart;
import com.shopwell.api.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomer_CustomerId(Long customerId);

    Optional<Cart> findCartByCustomer(Customer customer);
}
