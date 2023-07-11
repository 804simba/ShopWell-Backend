package com.shopwell.api.services.implementations;

import com.shopwell.api.exceptions.InvalidCredentialsException;
import com.shopwell.api.model.VOs.request.LoginRequestVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.model.entity.BaseUser;
import com.shopwell.api.model.entity.Customer;
import com.shopwell.api.repository.AdminRepository;
import com.shopwell.api.repository.CustomerRepository;
import com.shopwell.api.security.JwtService;
import com.shopwell.api.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final PasswordEncoder passwordEncoder;
    
    private final CustomerRepository customerRepository;
    
    private final AdminRepository adminRepository;
    
    private final JwtService jwtService;

    @SneakyThrows
    @Override
    public CustomerResponseVO authenticate(LoginRequestVO loginRequest) {

        BaseUser user = findEmail(loginRequest.getEmail());
        
        if(user.getStatus()) {
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
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
    private BaseUser findEmail(String email){
        var admin = adminRepository.findByEmail(email);
        var customer = customerRepository.findByEmail(email);
        if (admin.isPresent()) {
            return admin.get();
        } else if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new UsernameNotFoundException("Email does not exist");
        }
    }
}
