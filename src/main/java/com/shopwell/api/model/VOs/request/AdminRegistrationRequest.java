package com.shopwell.api.model.VOs.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Builder
public class AdminRegistrationRequest {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 30)
    private String adminFirstName;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 30)
    private String adminLastName;

    @NotNull
    @NotEmpty(message = "Email address must not be empty")
    @Size(min = 5, max = 50)
    @Email(message = "Enter email address")
    private String adminEmail;

    private String adminDateOfBirth;

    @NotNull
    @NotEmpty(message = "Enter your mobile number")
    @Size(min = 11, max = 20, message = "Mobile number must be 11 to 15 characters long")
    private String adminPhoneNumber;

    @NotNull
    @NotEmpty(message = "Enter your street address")
    @Size(min = 5, max = 50)
    private String adminStreetAddress;

    @NotNull
    @NotEmpty(message = "Enter your city")
    @Size(min = 5, max = 50)
    private String adminCity;

    @NotNull
    @NotEmpty(message = "Enter your password")
    private String adminPassword;

    private MultipartFile adminImage;
}
