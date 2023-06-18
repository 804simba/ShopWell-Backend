package com.shopwell.api.repository;

import com.shopwell.api.model.entity.Cart;
import com.shopwell.api.model.entity.CartItem;
import com.shopwell.api.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByProductAndCart(Product product, Cart cart);
}
