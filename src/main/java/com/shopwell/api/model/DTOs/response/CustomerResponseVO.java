package com.shopwell.api.model.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseVO {

    private String firstName;

    private String lastName;

    private String emailAddress;
}
