package com.shopwell.api.service;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.DTOs.request.CustomerRegistrationVO;
import com.shopwell.api.model.DTOs.response.ApiResponseVO;
import com.shopwell.api.model.DTOs.response.CustomerResponseVO;

public interface CustomerService {
    ApiResponseVO<?> registerCustomer(CustomerRegistrationVO registrationVO);

    CustomerResponseVO findCustomerByEmail(String email) throws CustomerNotFoundException;


}
