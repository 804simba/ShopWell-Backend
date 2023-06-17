package com.shopwell.api.service;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.CustomerRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;

public interface CustomerService {
    ApiResponseVO<?> registerCustomer(CustomerRegistrationVO registrationVO);

    CustomerResponseVO findCustomerByEmail(String email) throws CustomerNotFoundException;


}
