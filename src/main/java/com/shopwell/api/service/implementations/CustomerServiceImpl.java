package com.shopwell.api.service.implementations;

import com.shopwell.api.event_driven.RegisterEvent;
import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.CustomerRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.OTPConfirmation;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.repository.OTPRepository;
import com.shopwell.api.service.CustomerService;
import com.shopwell.api.utils.RandomValues;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;
    private final OTPRepository otpRepository;


    @Override
    public ApiResponseVO<?> registerCustomer(CustomerRegistrationVO registrationVO) {
        Customer customer = customerVOToEntity(registrationVO);
        Customer saveUser = customerRepository.save(customer);
        publisher.publishEvent(new RegisterEvent(customer,generatingOtp(customer)));

        return ApiResponseVO.builder()
                .message("Account created successfully")
                .payload(customerEntityToVO(saveUser)).build();
    }
    private String generatingOtp(Customer user)  {
        String otp = RandomValues.generateRandom();
        OTPConfirmation confirmationToken = new OTPConfirmation(otp, user);
        System.out.println("****");
        OTPConfirmation otpConfirmation = otpRepository.findId(user.getCustomerId());
        System.out.println("-----");
        if (otpConfirmation != null){
            otpRepository.delete(otpConfirmation);
        }
        System.out.println(otpConfirmation);
        otpRepository.save(confirmationToken);
        System.out.println(otp);

        return otp;

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
                .customerStatus(false)
                .customerPassword(passwordEncoder.encode(customerRegistrationVO.getCustomerPassword()))
                .customerCity(customerRegistrationVO.getCustomerCity())
                .customerDateOfBirth(Date.valueOf(LocalDate.now()))
                .customerStreetAddress(customerRegistrationVO.getCustomerStreetAddress())
                .build();
    }
}
