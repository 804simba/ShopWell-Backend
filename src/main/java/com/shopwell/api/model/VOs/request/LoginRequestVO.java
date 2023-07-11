package com.shopwell.api.model.VOs.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestVO {
    @NotNull
    @NotEmpty(message = "Enter your email")
    private String email;
    @NotNull
    @NotEmpty(message = "Enter your password")
    private String password;
}
