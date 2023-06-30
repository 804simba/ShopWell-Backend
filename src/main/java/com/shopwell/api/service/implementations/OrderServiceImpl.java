package com.shopwell.api.service.implementations;

import com.shopwell.api.exceptions.CartNotFoundException;
import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.OrderRequestVO;
import com.shopwell.api.model.VOs.response.OrderItemVO;
import com.shopwell.api.model.VOs.response.OrderResponseVO;
import com.shopwell.api.model.entity.*;
import com.shopwell.api.model.enums.OrderStatus;
import com.shopwell.api.repository.CartRepository;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.repository.OrderRepository;
import com.shopwell.api.service.CartService;
import com.shopwell.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final CartService cartService;

    @Override
    public OrderResponseVO placeOrder(Long customerId, OrderRequestVO orderRequestVO) {
        try {
            Customer foundCustomer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

            Cart cart = cartRepository.findCartByCustomer(foundCustomer)
                    .orElseThrow(() -> new CartNotFoundException("Cart not found"));
            List<CartItem> cartItems = cart.getCartItems();

            Order order = new Order();
            order.setShippingAddress(orderRequestVO.getShippingAddress());
            order.setPaymentMethod(orderRequestVO.getPaymentMethod());

            List<OrderItem> orderItems = cartItems.stream()
                    .map(this::mapCartItem).collect(Collectors.toList());

            orderItems.forEach(orderItem -> orderItem.setOrder(order));

            order.setOrderItems(orderItems);
            order.setOrderDate(Timestamp.from(Instant.now()));
            order.setOrderStatus(OrderStatus.PENDING);
            order.setCustomer(foundCustomer);

            Double totalCostOfGoods = cartService.calculateTotalPrice(customerId);

            order.setOrderTotal(BigDecimal.valueOf(totalCostOfGoods));

            orderRepository.save(order);

            return OrderResponseVO.builder()
                    .orderId(order.getOrderId())
                    .orderDate(order.getOrderDate().toString())
                    .orderStatus(OrderStatus.PENDING.name())
                    .build();

        } catch (CustomerNotFoundException e) {
            throw new RuntimeException("Failed place order" + e.getMessage());
        }
    }

    @Override
    public List<OrderResponseVO> getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Failed place order"));
        return order.getOrderItems().stream()
                .map(orderItem -> OrderResponseVO.builder()
                        .orderId(orderItem.getId())
                        .orderDate(order.getOrderDate().toString())
                        .orderStatus(order.getOrderStatus().name())
                        .orderItems(List.of(mapOrderItem(orderItem)))
                        .build()).collect(Collectors.toList());
    }

    @Override
    public String cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return "Order cancelled successfully. OrderId: " + order.getOrderId();
    }

    @Override
    public List<OrderResponseVO> getCustomerOrders(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        return customer.getOrders().stream()
                .map(order -> OrderResponseVO.builder()
                        .orderId(order.getOrderId())
                        .orderDate(order.getOrderDate().toString())
                        .orderStatus(order.getOrderStatus().name())
                        .orderItems(order.getOrderItems()
                                .stream().map(this::mapOrderItem)
                                .collect(Collectors.toList())).build()).collect(Collectors.toList());
    }

    @Override
    public String updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus(status);
        orderRepository.save(order);
        return "Order status updated";
    }

    private OrderItem mapCartItem(CartItem cartItem) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuotedPrice(cartItem.getQuotedPrice());
            orderItem.setQuantityOrdered(cartItem.getQuantityOrdered());
            orderItem.setProduct(cartItem.getProduct());

            return orderItem;
    }

    private OrderItemVO mapOrderItem(OrderItem orderItem) {
        return OrderItemVO.builder()
                .productId(orderItem.getProduct().getProductNumber())
                .productPrice(String.valueOf(orderItem.getQuotedPrice()))
                .quantityOrdered(String.valueOf(orderItem.getQuantityOrdered()))
                .build();
    }
}
