package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.paymentDTOs.*;
import com.shopwell.api.service.PaystackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PaystackController {
    private final PaystackService paystackService;

    @PostMapping("/create-plan")
    public CreatePlanResponse createPlan(@Validated @RequestBody CreatePlanRequest request) {
        return paystackService.createPlan(request);
    }

    @PostMapping("/initialize-payment")
    public InitializePaymentResponse initializePayment(@Validated @RequestBody InitializePaymentRequest request) throws Exception {
        return paystackService.initializePayment(request);
    }

    @GetMapping("/verify-payment/{reference}/{plan}/{customerId}")
    public PaymentVerificationResponse verifyPayment(@PathVariable("reference") final String reference,
                                                     @PathVariable("plan") final String plan,
                                                     @PathVariable("customerId") final Long customerId) throws Exception {
        return paystackService.paymentVerification(reference, plan, customerId);
    }
}
