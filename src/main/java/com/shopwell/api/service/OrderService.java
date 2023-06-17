package com.shopwell.api.service;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.OrderRequestVO;
import com.shopwell.api.model.VOs.response.OrderResponseVO;
import com.shopwell.api.model.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    OrderResponseVO placeOrder(Long customerId, OrderRequestVO orderRequestVO) throws CustomerNotFoundException;

    List<OrderResponseVO> getOrderDetails(Long orderId);

    String cancelOrder(Long orderId);

    List<OrderResponseVO> getCustomerOrders(Long customerId) throws CustomerNotFoundException;

    String updateOrderStatus(Long orderId, OrderStatus status);
}
