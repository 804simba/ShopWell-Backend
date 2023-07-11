package com.shopwell.api.services;

import com.shopwell.api.model.VOs.request.PaymentRequestVO;
import com.shopwell.api.model.VOs.request.paymentDTOs.PaymentVerificationResponse;
import com.shopwell.api.model.VOs.response.PaymentResponseVO;

public interface PaymentService {

    PaymentResponseVO initializePayment(PaymentRequestVO paymentRequestVO) throws Exception;

    PaymentVerificationResponse paymentVerification(String reference) throws Exception;
}
