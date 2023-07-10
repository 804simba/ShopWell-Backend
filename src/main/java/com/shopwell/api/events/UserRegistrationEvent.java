package com.shopwell.api.events;

import com.shopwell.api.model.entity.BaseUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserRegistrationEvent extends ApplicationEvent {
    private String otp;

    private BaseUser user;

    public UserRegistrationEvent(BaseUser user, String otp) {
        super(user);
        this.user = user;
        this.otp = otp;
    }
}
