package com.shopwell.api.service.implementations;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.exceptions.ProductNotFoundException;
import com.shopwell.api.model.DTOs.request.OrderRequestVO;
import com.shopwell.api.model.DTOs.request.OrderRequestVO.OrderItemVO;
import com.shopwell.api.model.DTOs.response.OrderResponseVO;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.Order;
import com.shopwell.api.model.entity.OrderItem;
import com.shopwell.api.model.entity.Product;
import com.shopwell.api.model.enums.OrderStatus;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.repository.OrderRepository;
import com.shopwell.api.repository.ProductRepository;
import com.shopwell.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderResponseVO placeOrder(Long customerId, OrderRequestVO orderRequestVO) {
        try {
            Customer foundCustomer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

            Order order = new Order();
            order.setShippingAddress(orderRequestVO.getShippingAddress());
            order.setPaymentMethod(orderRequestVO.getPaymentMethod());

            List<OrderItem> orderItems = orderRequestVO.getOrderItems().stream()
                    .map(this::mapOrderItemVO).collect(Collectors.toList());

            order.setOrderItems(orderItems);

            foundCustomer.getOrders().add(order);
            customerRepository.save(foundCustomer);

            return OrderResponseVO.builder()
                    .orderId(order.getOrderId())
                    .orderDate(order.getOrderDate().toString())
                    .orderStatus(OrderStatus.PENDING.name())
                    .orderItems(orderRequestVO.getOrderItems())
                    .build();

        } catch (CustomerNotFoundException e) {
            throw new RuntimeException("Failed place order" + e.getMessage());
        }
    }

    @Override
    public List<OrderResponseVO> getOrderDetails(Long orderId) {

        return null;
    }

    @Override
    public String cancelOrder(Long orderId) {
        return null;
    }

    @Override
    public List<OrderResponseVO> getCustomerOrders(Long customerId) {
        return null;
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus status) {

    }

    private OrderItem mapOrderItemVO(OrderItemVO orderItemVO) {
        try {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuotedPrice(orderItemVO.getProductPrice());
            orderItem.setQuantityOrdered(orderItem.getQuantityOrdered());
            orderItem.setDeliveryDate(orderItemVO.getDeliveryDate());

            Product product = productRepository.findById(orderItemVO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            orderItem.setProduct(product);

            return orderItem;
        } catch (ProductNotFoundException e) {
            throw new RuntimeException("Product not found");
        }
    }
}
