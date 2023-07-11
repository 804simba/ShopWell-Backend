package com.shopwell.api.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.shopwell.api.events.PaymentEvent;
import com.shopwell.api.model.VOs.request.PaymentRequestVO;
import com.shopwell.api.model.VOs.request.paymentDTOs.PaymentVerificationResponse;
import com.shopwell.api.model.VOs.response.PaymentResponseVO;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.PaymentDetail;
import com.shopwell.api.repository.PaystackPaymentRepository;
import com.shopwell.api.services.PaymentService;
import com.shopwell.api.utils.UserUtils;
import com.shopwell.api.utils.constants.PaystackConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaystackServiceImpl implements PaymentService {

    private final PaystackPaymentRepository paystackPaymentRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Value("${applyforme.paystack.secret.key}")
    private String paystackSecretKey;

    @Override
    public PaymentResponseVO initializePayment(PaymentRequestVO paymentRequestVO) throws Exception {
        PaymentResponseVO paymentResponseVO = null;

        try {
            Gson gson = new Gson();

            StringEntity postString = new StringEntity(gson.toJson(paymentRequestVO));

            HttpClient client = HttpClientBuilder.create().build();

            HttpPost post = new HttpPost(PaystackConstants.PAYSTACK_INIT_PAYMENT);

            post.setEntity(postString);

            post.addHeader("Content-Type", "application/json");

            post.addHeader("Authorization", "Bearer " + paystackSecretKey);

            StringBuilder result = new StringBuilder();

            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == PaystackConstants.STATUS_CODE_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to initialize payment now.");
            }
            ObjectMapper mapper = new ObjectMapper();
            paymentResponseVO = mapper.readValue(result.toString(), PaymentResponseVO.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return paymentResponseVO;
    }

    @Override
    public PaymentVerificationResponse paymentVerification(String reference) throws Exception {
        if (reference.isEmpty()) {
            throw new Exception("Reference must be provided in path.");
        }

        PaymentVerificationResponse paymentVerificationResponse;
        PaymentDetail paymentDetail = null;
        Customer customer = null;

        try {
            HttpClient client = HttpClientBuilder.create().build();

            HttpGet request = new HttpGet(PaystackConstants.PAYSTACK_VERIFY_PAYMENT + reference);

            request.addHeader("Accept", "application/json");

            request.addHeader("Authorization", "Bearer " + paystackSecretKey);

            StringBuilder result = new StringBuilder();

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == PaystackConstants.STATUS_CODE_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to verify payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();

            paymentVerificationResponse = mapper.readValue(result.toString(), PaymentVerificationResponse.class);

            if (paymentVerificationResponse == null || paymentVerificationResponse.getData().getStatus().equals("false")) {
                throw new Exception("An error occurred while verifying payment from Paystack");
            } else if (paymentVerificationResponse.getData().getStatus().equals("success")) {
                customer = UserUtils.getAuthenticatedUser(Customer.class);

                paymentDetail = PaymentDetail.builder()
                        .customer(customer)
                        .reference(paymentVerificationResponse.getData().getReference())
                        .amount(paymentVerificationResponse.getData().getAmount())
                        .gatewayResponse(paymentVerificationResponse.getData().getGatewayResponse())
                        .paidAt(paymentVerificationResponse.getData().getPaidAt())
                        .createdAt(paymentVerificationResponse.getData().getCreatedAt())
                        .channel(paymentVerificationResponse.getData().getChannel())
                        .currency(paymentVerificationResponse.getData().getCurrency())
                        .ipAddress(paymentVerificationResponse.getData().getIpAddress())
                        .build();
            }
        } catch (Exception e) {
            throw new Exception("Paystack Payment exception:" + e.getMessage());
        }

        paystackPaymentRepository.save(Objects.requireNonNull(paymentDetail));

        PaymentEvent paymentEvent = new PaymentEvent(
                customer,
                paymentVerificationResponse.getData().getReference(),
                paymentVerificationResponse.getData().getAmount().toString());

        eventPublisher.publishEvent(paymentEvent);

        return paymentVerificationResponse;
    }
}
