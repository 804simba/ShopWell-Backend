package com.shopwell.api.service;

import com.shopwell.api.model.VOs.request.paymentDTOs.*;

public interface PaystackService {

    CreatePlanResponse createPlan(CreatePlanRequest createPlanRequest);

    InitializePaymentResponse initializePayment(InitializePaymentRequest initializePaymentRequest) throws Exception;

    PaymentVerificationResponse paymentVerification(String reference, String plan, Long id) throws Exception;
}
