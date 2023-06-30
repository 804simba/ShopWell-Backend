package com.shopwell.api.controller;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.OrderRequestVO;
import com.shopwell.api.model.VOs.response.OrderResponseVO;
import com.shopwell.api.model.enums.OrderStatus;
import com.shopwell.api.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<OrderResponseVO> placeOrder(@RequestBody @Valid final OrderRequestVO orderRequestVO) throws CustomerNotFoundException {
        log.info(String.format("Placing order for customer %d: order %s: ", orderRequestVO.getCustomerId(), orderRequestVO));
        OrderResponseVO response = orderService.placeOrder(orderRequestVO.getCustomerId(), orderRequestVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderResponseVO>> getOrderDetails(@PathVariable("orderId") final Long orderId) {
        log.info(String.format("Fetching order details for orderID %d: ", orderId));
        List<OrderResponseVO> response = orderService.getOrderDetails(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") final Long orderId) {
        log.info(String.format("Cancelling order for orderID %d: ", orderId));
        String response = orderService.cancelOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponseVO>> getCustomerOrders(@PathVariable("customerId") final Long customerId) throws CustomerNotFoundException {
        log.info(String.format("Fetching orders for customer %d: ", customerId));
        List<OrderResponseVO> response = orderService.getCustomerOrders(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable("orderId") final Long orderId, @RequestParam final String status) {
        log.info(String.format("Updating order status for order %d: ", orderId));
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        String message = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(message);
    }
}
