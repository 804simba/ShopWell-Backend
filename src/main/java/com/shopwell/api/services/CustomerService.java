package com.shopwell.api.services;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.RegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    ApiResponseVO<?> registerCustomer(RegistrationVO registrationVO);

    CustomerResponseVO findCustomerByEmail(String email) throws CustomerNotFoundException;

    List<Customer> findCustomersByBirthDate(int monthValue, int dayOfMonth);
}
