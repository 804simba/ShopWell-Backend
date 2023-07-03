package com.shopwell.api.event_driven;

import com.shopwell.api.model.entity.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PaymentEvent extends ApplicationEvent {
    private final Customer customer;

    private String reference;

    private String amount;

    public PaymentEvent(Customer customer, String reference, String amount) {
        super(customer);
        this.customer = customer;
        this.reference = reference;
        this.amount = amount;
    }
}
