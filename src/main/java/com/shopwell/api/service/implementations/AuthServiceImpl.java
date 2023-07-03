package com.shopwell.api.service.implementations;

import com.shopwell.api.exceptions.InvalidCredentialsException;
import com.shopwell.api.model.VOs.request.CustomerLoginRequestVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.repository.OTPRepository;
import com.shopwell.api.security.JwtService;
import com.shopwell.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    @SneakyThrows

    @Override
    public CustomerResponseVO authenticate(CustomerLoginRequestVO customerLoginRequestVO) {

        Customer user =findEmail(customerLoginRequestVO.getEmail());
        if(user.getCustomerStatus()) {
            if (!passwordEncoder.matches(customerLoginRequestVO.getCustomerPassword(), user.getPassword())) {
                throw new InvalidCredentialsException("Invalid Password");
            }
            String jwt = jwtService.generateToken(user);
            String refresh = jwtService.generateRefreshToken(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getCustomerEmail(),user.getPassword());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return CustomerResponseVO.builder()
                    .emailAddress(user.getCustomerEmail())
                    .object("Token " +jwt + " || Refresh :"+refresh)
                    .firstName(user.getCustomerFirstName())
                    .lastName(user.getCustomerLastName())
                    .build();
        }else{
            return CustomerResponseVO.builder()
                    .emailAddress(user.getCustomerEmail())
                    .object("ERROR")
                    .firstName(null)
                    .lastName(null)
                    .build();
        }
    }
    private Customer findEmail(String email){
        return customerRepository.findCustomerByCustomerEmail(email).orElseThrow(()-> new RuntimeException("USER NOT FOUND"));
    }
}
