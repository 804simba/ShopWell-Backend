package com.shopwell.api.controller;

import com.shopwell.api.exceptions.CustomerNotFoundException;
import com.shopwell.api.model.VOs.request.CustomerEmailRequestVO;
import com.shopwell.api.model.VOs.request.CustomerRegistrationVO;
import com.shopwell.api.model.VOs.request.EmployeeRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseVO<String>> registerCustomer(@RequestBody CustomerRegistrationVO customerRegistrationVO) {
        log.info("Registering Customer: " + customerRegistrationVO);
        customerService.registerCustomer(customerRegistrationVO);

        return ResponseEntity.ok(ApiResponseVO.<String>builder()
                .message("Customer saved successfully")
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponseVO<CustomerResponseVO>> getCustomerByEmail(@RequestBody CustomerEmailRequestVO customerEmailRequestVO) throws CustomerNotFoundException {
        log.info("Getting Customer by email: " + customerEmailRequestVO);
        CustomerResponseVO customer = customerService.findCustomerByEmail(customerEmailRequestVO.getEmail());

        return ResponseEntity.ok(ApiResponseVO.<CustomerResponseVO>builder()
                .payload(customer)
                .build());
    }
}
