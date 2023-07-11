package com.shopwell.api.services;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.OrderRequestVO;
import com.shopwell.api.model.VOs.response.OrderResponseVO;
import com.shopwell.api.model.entity.Order;
import com.shopwell.api.model.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    Order findByOrderId(Long orderId);

    OrderResponseVO placeOrder(OrderRequestVO orderRequestVO) throws CustomerNotFoundException;

    List<OrderResponseVO> getOrderDetails(Long orderId);

    String cancelOrder(Long orderId);

    List<OrderResponseVO> getCustomerOrders(int pageNumber, int pageSize) throws CustomerNotFoundException;

    String updateOrderStatus(Long orderId, OrderStatus status);
}
