package com.shopwell.api.service;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.CustomerRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    ApiResponseVO<?> registerCustomer(CustomerRegistrationVO registrationVO);

    CustomerResponseVO findCustomerByEmail(String email) throws CustomerNotFoundException;

    List<Customer> findCustomersByBirthDate(int monthValue, int dayOfMonth);
}
