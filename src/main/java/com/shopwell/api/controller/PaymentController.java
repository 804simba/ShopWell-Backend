package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.PaymentRequestVO;
import com.shopwell.api.model.VOs.request.paymentDTOs.PaymentVerificationResponse;
import com.shopwell.api.model.VOs.response.PaymentResponseVO;
import com.shopwell.api.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Payment")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
            summary = "This is an endpoint for initializing a payment using Paystack.",
            responses = {
                    @ApiResponse(description = "Payment plan created", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    @PostMapping("/initialize")
    public PaymentResponseVO initializePayment(@Validated @RequestBody PaymentRequestVO request) throws Exception {
        return paymentService.initializePayment(request);
    }

    @Operation(
            summary = "This is an endpoint for verifying a payment using Paystack.",
            responses = {
                    @ApiResponse(description = "Payment successful", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")
            }
    )
    @GetMapping("/verify/{reference}")
    public PaymentVerificationResponse verifyPayment(@PathVariable("reference") final String reference) throws Exception {
        return paymentService.paymentVerification(reference);
    }
}
