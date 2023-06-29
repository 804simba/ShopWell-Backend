package com.shopwell.api.service.implementations;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.CustomerRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.service.CustomerService;
import com.shopwell.api.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public ApiResponseVO<?> registerCustomer(CustomerRegistrationVO registrationVO) {
        Customer customer = customerVOToEntity(registrationVO);
        return ApiResponseVO.builder()
                .message("Account created successfully")
                .payload(customerEntityToVO(customerRepository.save(customer))).build();
    }

    @Override
    public CustomerResponseVO findCustomerByEmail(String email) throws CustomerNotFoundException {
        Customer foundCustomer = customerRepository
                .findCustomerByCustomerEmail(email).orElseThrow(() -> new CustomerNotFoundException(String.format("Custome with email %s not found", email)));
        return customerEntityToVO(foundCustomer);
    }

    private CustomerResponseVO customerEntityToVO(Customer customer) {
        return CustomerResponseVO.builder()
                .firstName(customer.getCustomerFirstName())
                .lastName(customer.getCustomerLastName())
                .emailAddress(customer.getCustomerEmail())
                .build();
    }

    private Customer customerVOToEntity(CustomerRegistrationVO customerRegistrationVO) {
        return Customer.builder()
                .customerFirstName(customerRegistrationVO.getCustomerFirstName())
                .customerLastName(customerRegistrationVO.getCustomerLastName())
                .customerEmail(customerRegistrationVO.getCustomerEmail())
                .customerPhoneNumber(customerRegistrationVO.getCustomerPhoneNumber())
                .customerCity(customerRegistrationVO.getCustomerCity())
                .customerDateOfBirth(DateUtils.getDate(customerRegistrationVO.getCustomerDateOfBirth()))
                .customerStreetAddress(customerRegistrationVO.getCustomerStreetAddress())
                .build();
    }
}
