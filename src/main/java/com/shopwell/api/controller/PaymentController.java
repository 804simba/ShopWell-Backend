package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.paymentDTOs.*;
import com.shopwell.api.service.PaystackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Payment")
@SecurityRequirement(name = "Bearer Authentication")
public class PaymentController {
    private final PaystackService paystackService;

    @Operation(
            summary = "This is an endpoint for creating a payment plan using Paystack.",
            responses = {
                    @ApiResponse(description = "Payment plan created", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Product not found", responseCode = "404")
            }
    )
    @PostMapping("/create-plan")
    public CreatePlanResponse createPlan(@Validated @RequestBody CreatePlanRequest request) {
        return paystackService.createPlan(request);
    }

    @Operation(
            summary = "This is an endpoint for initializing a payment using Paystack.",
            responses = {
                    @ApiResponse(description = "Payment plan created", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    @PostMapping("/initialize")
    public InitializePaymentResponse initializePayment(@Validated @RequestBody InitializePaymentRequest request) throws Exception {
        return paystackService.initializePayment(request);
    }

    @Operation(
            summary = "This is an endpoint for verifying a payment using Paystack.",
            responses = {
                    @ApiResponse(description = "Payment successful", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    @GetMapping("/verify/{reference}/{plan}/{customerId}")
    public PaymentVerificationResponse verifyPayment(@PathVariable("reference") final String reference,
                                                     @PathVariable("plan") final String plan,
                                                     @PathVariable("customerId") final Long customerId) throws Exception {
        return paystackService.paymentVerification(reference, plan, customerId);
    }
}
