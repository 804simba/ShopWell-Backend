package com.shopwell.api.controller;

import com.shopwell.api.services.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/checkout")
@Tag(name = "Checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @Operation(
            summary = "This is an endpoint for initializing a checking out feature.",
            responses = {
                    @ApiResponse(description = "Checkout successful", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    @GetMapping
    @SneakyThrows
    public ResponseEntity<String> checkout(Long orderId) {
        return ResponseEntity.ok(checkoutService.checkout(orderId));
    }
}
