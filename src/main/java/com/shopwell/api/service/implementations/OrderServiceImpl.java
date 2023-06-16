package com.shopwell.api.service.implementations;

import com.shopwell.api.model.DTOs.request.OrderRequestVO;
import com.shopwell.api.model.DTOs.response.OrderResponseVO;
import com.shopwell.api.model.enums.OrderStatus;
import com.shopwell.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public String placeOrder(Long customerId, OrderRequestVO orderRequestVO) {
        return null;
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
}
