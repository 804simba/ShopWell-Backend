package com.shopwell.api.model.DTOs.request;

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
public class VendorRegistrationVO {

    @NotNull
    @NotEmpty(message = "Enter vendor name")
    @Size(min = 5, max = 30, message = "Vendor name must be 5 to 20 characters long")
    private String vendorName;

    @NotNull
    @NotEmpty(message = "Enter your mobile number")
    @Size(min = 11, max = 15, message = "Mobile number must be 11 to 15 characters long")
    private String vendorPhoneNumber;

    @NotNull
    @NotEmpty(message = "Enter vendor street address")
    @Size(min = 5, max = 30)
    private String vendorStreetAddress;

    @NotNull
    @NotEmpty(message = "Enter vendor city")
    @Size(min = 5, max = 30)
    private String vendorCity;

    @NotNull
    @NotEmpty(message = "Enter your vendor state")
    @Size(min = 5, max = 30)
    private String vendorState;

    @NotNull
    @NotEmpty(message = "Enter your vendor zip-code")
    @Size(min = 5, max = 30)
    private String vendorZipCode;

    @NotNull
    @NotEmpty(message = "Email address must be provided")
    @Email(message = "Enter vendor email address")
    @Size(min = 5, max = 30)
    private String vendorEmailAddress;

    @NotNull
    @NotEmpty(message = "Website address must be provided")
    @Email(message = "Enter vendor website address")
    @Size(min = 5, max = 30)
    private String vendorWebsiteAddress;
}
