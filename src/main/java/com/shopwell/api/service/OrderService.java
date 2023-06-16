package com.shopwell.api.service;

import com.shopwell.api.model.DTOs.request.OrderRequestVO;
import com.shopwell.api.model.DTOs.response.OrderResponseVO;
import com.shopwell.api.model.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    String placeOrder(Long customerId, OrderRequestVO orderRequestVO);

    List<OrderResponseVO> getOrderDetails(Long orderId);

    String cancelOrder(Long orderId);

    List<OrderResponseVO> getCustomerOrders(Long customerId);

    void updateOrderStatus(Long orderId, OrderStatus status);
}
