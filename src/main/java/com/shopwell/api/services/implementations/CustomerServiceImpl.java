package com.shopwell.api.services.implementations;

import com.shopwell.api.events.UserRegistrationEvent;
import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.RegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.model.entity.OTP;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.services.CustomerService;
import com.shopwell.api.services.OTPService;
import com.shopwell.api.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher publisher;

    private final OTPService<Customer> otpService;

    private final MapperUtils mapperUtils;


    @Override
    public ApiResponseVO<?> registerCustomer(RegistrationVO registrationVO) {
        Customer customer = mapperUtils.customerVOToCustomerEntity(registrationVO);

        customer.setPassword(passwordEncoder.encode(registrationVO.getPassword()));
        Customer saveUser = customerRepository.save(customer);

        OTP otpEntity = otpService.generateOTP(customer);
        otpEntity.setUser(customer);
        otpService.saveOTP(otpEntity);

        String otp = otpEntity.getOtp();
        publisher.publishEvent(new UserRegistrationEvent(customer, otp));

        return ApiResponseVO.builder()
                .message("Account created successfully")
                .payload(mapperUtils.customerEntityToCustomerResponseVO(saveUser)).build();
    }

    @Override
    public CustomerResponseVO findCustomerByEmail(String email) throws CustomerNotFoundException {
        Customer foundCustomer = customerRepository
                .findByEmail(email).orElseThrow(() -> new CustomerNotFoundException(String.format("Custome with email %s not found", email)));
        return mapperUtils.customerEntityToCustomerResponseVO(foundCustomer);
    }

    @Override
    public List<Customer> findCustomersByBirthDate(int monthValue, int dayOfMonth) {
        return customerRepository.findCustomersByDateOfBirth(monthValue, dayOfMonth);
    }
}
