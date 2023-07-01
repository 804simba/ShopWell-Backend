package com.shopwell.api.event_driven;

import com.shopwell.api.model.entity.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class RegisterEvent  extends ApplicationEvent {
    private String otp;
    private Customer customer;
    public RegisterEvent(Customer customer,String otp) {
        super(customer);
        this.customer=customer;
        this.otp=otp;
    }
}
