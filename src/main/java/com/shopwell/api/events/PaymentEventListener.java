package com.shopwell.api.events;

import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListener implements ApplicationListener<PaymentEvent> {
    private final EmailService emailService;
    @Override
    public void onApplicationEvent(@NonNull PaymentEvent event) {
        Customer customer = event.getCustomer();
        String reference = event.getReference();
        String amount = event.getAmount();

        String email = customer.getEmail();

        String subject = "Payment Confirmation";
        String text = String.format("Your payment with reference: %s for amount: ₦%s was successful", reference, amount);

        emailService.sendEmail(email, subject, text);
    }
}
