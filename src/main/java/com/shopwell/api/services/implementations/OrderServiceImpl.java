package com.shopwell.api.services.implementations;

import com.shopwell.api.exceptions.CartNotFoundException;
import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.OrderRequestVO;
import com.shopwell.api.model.VOs.response.OrderResponseVO;
import com.shopwell.api.model.entity.*;
import com.shopwell.api.model.enums.OrderStatus;
import com.shopwell.api.repository.CartRepository;
import com.shopwell.api.repository.OrderRepository;
import com.shopwell.api.services.CartService;
import com.shopwell.api.services.OrderService;
import com.shopwell.api.utils.MapperUtils;
import com.shopwell.api.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final CartService cartService;
    private final MapperUtils mapperUtils;

    @Override
    public OrderResponseVO placeOrder(OrderRequestVO orderRequestVO) {
        try {
            Customer customer = UserUtils.getAuthenticatedUser(Customer.class);

            if (customer == null) {
                throw new CustomerNotFoundException("Customer not found");
            }

            Cart cart = cartRepository.findCartByCustomer(customer)
                    .orElseThrow(() -> new CartNotFoundException("Cart not found"));
            List<CartItem> cartItems = cart.getCartItems();

            Order order = new Order();
            order.setShippingAddress(orderRequestVO.getShippingAddress());
            order.setPaymentMethod(orderRequestVO.getPaymentMethod());

            List<OrderItem> orderItems = cartItems.stream()
                    .map(mapperUtils::cartItemToOrderItem).collect(Collectors.toList());

            orderItems.forEach(orderItem -> orderItem.setOrder(order));

            order.setOrderItems(orderItems);
            order.setOrderDate(Timestamp.from(Instant.now()));
            order.setOrderStatus(OrderStatus.PENDING);
            order.setCustomer(customer);

            Double totalCostOfGoods = cartService.calculateTotalPrice(customer.getCustomerEmail());

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
                        .orderItems(List.of(mapperUtils.orderItemToOrderItemVO(orderItem)))
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
    public List<OrderResponseVO> getCustomerOrders(int pageNumber, int pageSize) throws CustomerNotFoundException {
        Customer customer = UserUtils.getAuthenticatedUser(Customer.class);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Order> ordersPage = orderRepository.findByCustomer(customer, pageable);

        return ordersPage.stream()
                .map(order -> OrderResponseVO.builder()
                        .orderId(order.getOrderId())
                        .orderDate(order.getOrderDate().toString())
                        .orderStatus(order.getOrderStatus().name())
                        .orderItems(order.getOrderItems()
                                .stream().map(mapperUtils::orderItemToOrderItemVO)
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
}
