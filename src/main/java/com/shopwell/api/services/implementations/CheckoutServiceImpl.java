package com.shopwell.api.services.implementations;

import com.shopwell.api.model.VOs.request.PaymentRequestVO;
import com.shopwell.api.model.VOs.request.paymentDTOs.PaymentVerificationResponse;
import com.shopwell.api.model.VOs.response.PaymentResponseVO;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.Order;
import com.shopwell.api.model.enums.OrderStatus;
import com.shopwell.api.services.CheckoutService;
import com.shopwell.api.services.OrderService;
import com.shopwell.api.services.PaymentService;
import com.shopwell.api.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final PaymentService paymentService;

    private final OrderService orderService;

    @Override
    public String checkout(Long orderId) {
        try {
            Customer customer = UserUtils.getAuthenticatedUser(Customer.class);

            Double productPrice = calculateTotalCostOfProducts(customer);

            PaymentRequestVO paymentRequestVO = new PaymentRequestVO(
                    customer.getEmail(),
                    productPrice
            );
            Order order = orderService.findByOrderId(orderId);
            orderService.updateOrderStatus(order.getOrderId(), OrderStatus.PROCESSING);
            PaymentResponseVO response = paymentService.initializePayment(paymentRequestVO);

            if (response.getStatus()) {
                String reference = response.getData().getReference();

                PaymentVerificationResponse verificationResponse =
                        paymentService.paymentVerification(reference);

                if (verificationResponse.getData().getStatus().equals("success")) {
                    orderService.updateOrderStatus(order.getOrderId(), OrderStatus.SHIPPED);
                } else {
                    orderService.updateOrderStatus(order.getOrderId(), OrderStatus.CANCELLED);
                }
            }
        } catch (Exception e) {
            log.info("Error during checkout: {}", e.getMessage());
        }
        return "Order was successful";
    }

    private Double calculateTotalCostOfProducts(Customer customer) {
        return customer.getCart().getCartItems().stream()
                .mapToDouble(cartItem -> Integer.parseInt(String.valueOf(cartItem.getQuotedPrice())) * cartItem.getQuantityOrdered()).sum();
    }
}
