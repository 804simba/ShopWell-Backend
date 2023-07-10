package com.shopwell.api.services.implementations;

import com.shopwell.api.exceptions.InvalidCredentialsException;
import com.shopwell.api.model.VOs.request.CustomerLoginRequestVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.security.JwtService;
import com.shopwell.api.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        if(user.getStatus()) {
            if (!passwordEncoder.matches(customerLoginRequestVO.getPassword(), user.getPassword())) {
                throw new InvalidCredentialsException("Invalid Password");
            }
            String jwt = jwtService.generateToken(user);
            String refresh = jwtService.generateRefreshToken(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),user.getPassword());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return CustomerResponseVO.builder()
                    .emailAddress(user.getEmail())
                    .object("Token " +jwt + " || Refresh :"+refresh)
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .build();
        }else{
            return CustomerResponseVO.builder()
                    .emailAddress(user.getEmail())
                    .object("ERROR")
                    .build();
        }
    }
    private Customer findEmail(String email){
        return customerRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("USER NOT FOUND"));
    }
}
