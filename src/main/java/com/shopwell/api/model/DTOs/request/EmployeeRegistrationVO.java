package com.shopwell.api.model.DTOs.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class EmployeeRegistrationVO {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 30)
    private String employeeFirstName;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 30)
    private String employeeLastName;

    @NotNull
    @NotEmpty(message = "Email address must not be empty")
    @Size(min = 5, max = 30)
    @Email(message = "Enter email address")
    private String employeeEmail;

    @Temporal(TemporalType.DATE)
    private Timestamp employeeDateOfBirth;

    @NotNull
    @NotEmpty(message = "Enter your mobile number")
    @Size(min = 11, max = 15, message = "Mobile number must be 11 to 15 characters long")
    private String employeePhoneNumber;

    @NotNull
    @NotEmpty(message = "Enter your street address")
    @Size(min = 5, max = 30)
    private String employeeStreetAddress;

    @NotNull
    @NotEmpty(message = "Enter your city")
    @Size(min = 5, max = 30)
    private String employeeCity;
}
