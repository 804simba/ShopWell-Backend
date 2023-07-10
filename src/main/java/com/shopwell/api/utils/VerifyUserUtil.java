package com.shopwell.api.utils;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.entity.BaseUser;
import com.shopwell.api.repository.AdminRepository;
import com.shopwell.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerifyUserUtil {
    private final CustomerRepository customerRepository;

    private final AdminRepository adminRepository;

    public BaseUser verifyIfCustomerOrAdminEmail(String email) throws CustomerNotFoundException {
        BaseUser user;
        if (customerRepository.existsByEmail(email)) {
            user = customerRepository.findByEmail(email).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        } else if (adminRepository.existsByEmail(email)) {
            user = adminRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("AdminUser not found"));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}