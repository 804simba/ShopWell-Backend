package com.shopwell.api.security;

import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer appUser = customerRepository.findCustomerByCustomerEmail(email)
                .orElseThrow(()->new UsernameNotFoundException(email));
        return new Customer(appUser.getCustomerEmail(),appUser.getPassword());
    }
}
