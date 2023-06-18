package com.shopwell.api.model.VOs.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerEmailRequestVO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
}
