package com.shopwell.api.controller;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.OrderRequestVO;
import com.shopwell.api.model.VOs.response.OrderResponseVO;
import com.shopwell.api.model.enums.OrderStatus;
import com.shopwell.api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Order")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "This is an endpoint for placing an order.",
            responses = {
                    @ApiResponse(description = "Order created", responseCode = "201"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Product not found", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    @PostMapping("/place-order")
    public ResponseEntity<OrderResponseVO> placeOrder(@RequestBody @Valid final OrderRequestVO orderRequestVO) throws CustomerNotFoundException {
        OrderResponseVO response = orderService.placeOrder(orderRequestVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "This is an endpoint for getting details for an order.",
            responses = {
                    @ApiResponse(description = "Customer Order found", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Order not found", responseCode = "404")
            }
    )
    @GetMapping(value = "/{orderId}")
    public ResponseEntity<List<OrderResponseVO>> getOrderDetails(@Parameter(name = "Order to find by ID") @PathVariable("orderId") final Long orderId) {
        log.info(String.format("Fetching order details for orderID %d: ", orderId));
        List<OrderResponseVO> response = orderService.getOrderDetails(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "This is an endpoint to cancel an order.",
            responses = {
                    @ApiResponse(description = "Customer Order cancelled", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Order not found", responseCode = "404")
            }
    )
    @PutMapping(value = "/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@Parameter(name = "Order to cancel by ID") @PathVariable("orderId") final Long orderId) {
        log.info(String.format("Cancelling order for orderID %d: ", orderId));
        String response = orderService.cancelOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "This is an endpoint to get all orders for a customer.",
            responses = {
                    @ApiResponse(description = "Customer Orders found", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Orders not found", responseCode = "404")
            }
    )
    @PostMapping("/customer")
    public ResponseEntity<List<OrderResponseVO>> getCustomerOrders(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                   @RequestParam(name = "pageSize", defaultValue = "20") int pageSize) throws CustomerNotFoundException {
        List<OrderResponseVO> response = orderService.getCustomerOrders(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "This is an endpoint to update an order status.",
            responses = {
                    @ApiResponse(description = "Customer Order status updated", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Order not found", responseCode = "404")
            }
    )
    @PutMapping(value = "/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@Parameter(name = "Order to update its status") @PathVariable("orderId") final Long orderId, @RequestParam final String status) {
        log.info(String.format("Updating order status for order %d: ", orderId));
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        String message = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(message);
    }
}
