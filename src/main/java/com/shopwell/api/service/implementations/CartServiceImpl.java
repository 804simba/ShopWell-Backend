package com.shopwell.api.service.implementations;

import com.shopwell.api.model.VOs.request.CartItemVO;
import com.shopwell.api.model.entity.Cart;
import com.shopwell.api.model.entity.CartItem;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.Product;
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
    public List<CartItemVO> getCartItems(Long customerId) {
        Cart cart = getOrCreateCart(customerId);
        List<CartItem> cartItems = cart.getCartItems();

        return cartItems.stream()
                .map(this::mapToCartItemVO)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal calculateTotalPrice(Long customerId) {
        Cart cart = getOrCreateCart(customerId);
        List<CartItem> cartItems = cart.getCartItems();

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            BigDecimal productPrice = cartItem.getProduct().getProductPrice();
            int quantity = cartItem.getCartItemQuantity();
            BigDecimal itemPrice = productPrice.multiply(BigDecimal.valueOf(quantity));
            totalPrice = totalPrice.add(itemPrice);
        }

        return totalPrice;
    }

    private CartItem getCartItemByProductAndCart(Product product, Cart cart) {
        return cartItemRepository.findByProductAAndCart(product, cart);
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

    private CartItemVO mapToCartItemVO(CartItem cartItem) {
        return CartItemVO.builder()
                .productId(cartItem.getProduct().getProductNumber())
                .productName(cartItem.getProduct().getProductName())
                .quantity(cartItem.getCartItemQuantity())
                .price(cartItem.getProduct().getProductPrice())
                .build();
    }
}
