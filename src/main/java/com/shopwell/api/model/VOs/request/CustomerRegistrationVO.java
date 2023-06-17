package com.shopwell.api.model.VOs.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomerRegistrationVO {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 30)
    private String customerFirstName;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 30)
    private String customerLastName;

    @NotNull
    @NotEmpty(message = "Email address must not be empty")
    @Size(min = 5, max = 30)
    @Email(message = "Enter email address")
    private String customerEmail;

    private String customerDateOfBirth;

    @NotNull
    @NotEmpty(message = "Enter your mobile number")
    @Size(min = 11, max = 15, message = "Mobile number must be 11 to 15 characters long")
    private String customerPhoneNumber;

    @NotNull
    @NotEmpty(message = "Enter your street address")
    @Size(min = 5, max = 30)
    private String customerStreetAddress;

    @NotNull
    @NotEmpty(message = "Enter your city")
    @Size(min = 5, max = 30)
    private String customerCity;
}
