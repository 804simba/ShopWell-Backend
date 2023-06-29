package com.shopwell.api.service.implementations;

import com.shopwell.api.model.VOs.request.CartItemVO;
import com.shopwell.api.model.VOs.response.CartItemResponseVO;
import com.shopwell.api.model.entity.*;
import com.shopwell.api.repository.CartItemRepository;
import com.shopwell.api.repository.CartRepository;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final CustomerRepository customerRepository;

    @Override
    public String addProductToCart(Product product, Long customerId, int quantity) {
        Cart cart = getOrCreateCart(customerId);
        CartItem cartItem = getCartItemByProductAndCart(product, cart);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setCartItemQuantity(quantity);
        } else {
            cartItem.setCartItemQuantity(cartItem.getCartItemQuantity() + quantity);
        }

        cartItemRepository.save(cartItem);
        return "Product added to cart successfully";
    }

    @Override
    public String removeProductFromCart(Product product, Long customerId) {
        Cart cart = getOrCreateCart(customerId);
        CartItem cartItem = getCartItemByProductAndCart(product, cart);

        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
        }
        return "Product removed from cart successfully";
    }

    @Override
    public List<CartItemResponseVO> getCartItems(Long customerId) {
        Cart cart = getOrCreateCart(customerId);
        List<CartItem> cartItems = cart.getCartItems();

        return cartItems.stream()
                .map(this::mapToCartItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Double calculateTotalPrice(Long customerId) {
        Cart cart = getOrCreateCart(customerId);
        List<CartItem> cartItems = cart.getCartItems();

        double totalPrice = 0.0;

        for (CartItem cartItem : cartItems) {
            Double productPrice = cartItem.getProduct().getProductPrice();
            int quantity = cartItem.getCartItemQuantity();
            double itemPrice = productPrice * quantity;
            totalPrice = totalPrice + itemPrice;
        }

        return totalPrice;
    }

    private CartItem getCartItemByProductAndCart(Product product, Cart cart) {
        return cartItemRepository.findByProductAndCart(product, cart);
    }

    private Cart getOrCreateCart(Long customerId) {
        Customer foundCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return cartRepository.findByCustomer_CustomerId(customerId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setCustomer(foundCustomer);
                    return cartRepository.save(cart);
                });
    }

    private CartItemResponseVO mapToCartItemResponse(CartItem cartItem) {
        List<ProductImage> productImages = cartItem.getProduct().getProductImageURLs();
        String productImageURL = "";

        if (!productImages.isEmpty()) {
            productImageURL = productImages.get(0).getImageUrl();
        }

        return CartItemResponseVO.builder()
                .productId(cartItem.getProduct().getProductNumber())
                .productName(cartItem.getProduct().getProductName())
                .quantity(cartItem.getCartItemQuantity())
                .productPrice(String.valueOf(cartItem.getProduct().getProductPrice()))
                .productImage(productImageURL)
                .build();
    }
}
