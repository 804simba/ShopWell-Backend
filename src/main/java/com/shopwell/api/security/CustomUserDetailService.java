package com.shopwell.api.security;

import com.shopwell.api.model.entity.AdminUser;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.repository.AdminRepository;
import com.shopwell.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findCustomerByCustomerEmail(email);
        if (customer.isPresent()) {
            return new User(customer.get().getUsername(), customer.get().getPassword(), new ArrayList<>());
        }

        Optional<AdminUser> admin = adminRepository.findByAdminEmail(email);
        if (admin.isPresent()) {
            return new User(admin.get().getUsername(), admin.get().getPassword(), new ArrayList<>());
        }

        throw new UsernameNotFoundException("Username not found for " + email);
    }
}
