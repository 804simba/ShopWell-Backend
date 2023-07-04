package com.shopwell.api.service.implementations;

import com.shopwell.api.event_driven.RegisterEvent;
import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.CustomerRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.OTPConfirmation;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.service.CustomerService;
import com.shopwell.api.service.image.CustomerImageService;
import com.shopwell.api.utils.MapperUtils;
import com.shopwell.api.utils.RandomValues;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher publisher;

    private final OtpServiceImpl otpService;

    private final MapperUtils mapperUtils;

    private final CustomerImageService customerImageService;


    @Override
    public ApiResponseVO<?> registerCustomer(CustomerRegistrationVO registrationVO) {
        Customer customer = mapperUtils.customerVOToCustomerEntity(registrationVO);
        Random random = new Random();
        Long imageId = random.nextLong(9000) + 1000;

        String imageURL = customerImageService.uploadImage(imageId, registrationVO.getCustomerImage());
        customer.setCustomerImageURL(imageURL);

        customer.setCustomerPassword(passwordEncoder.encode(registrationVO.getCustomerPassword()));
        Customer saveUser = customerRepository.save(customer);
        publisher.publishEvent(new RegisterEvent(customer,generatingOtp(customer)));

        return ApiResponseVO.builder()
                .message("Account created successfully")
                .payload(mapperUtils.customerEntityToCustomerResponseVO(saveUser)).build();
    }
    private String generatingOtp(Customer customer)  {
        String otp = RandomValues.generateRandom();
        OTPConfirmation confirmationToken = new OTPConfirmation(otp, customer);
        otpService.sendOtp(customer,otp,confirmationToken);
        return otp;

    }

    @Override
    public CustomerResponseVO findCustomerByEmail(String email) throws CustomerNotFoundException {
        Customer foundCustomer = customerRepository
                .findCustomerByCustomerEmail(email).orElseThrow(() -> new CustomerNotFoundException(String.format("Custome with email %s not found", email)));
        return mapperUtils.customerEntityToCustomerResponseVO(foundCustomer);
    }

    @Override
    public List<Customer> findCustomersByBirthDate(int monthValue, int dayOfMonth) {
        return customerRepository.findByCustomersDateOfBirth(monthValue, dayOfMonth);
    }
}
