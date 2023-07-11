package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.CustomerEmailRequestVO;
import com.shopwell.api.model.VOs.request.RegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.model.VOs.response.CustomerResponseVO;
import com.shopwell.api.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "This is an endpoint for registering customers."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer registered",
            content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Invalid / malformed request"),
            @ApiResponse(responseCode = "403", description = "Invalid / expired token"),
    })
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseVO<String>> registerCustomer(@RequestBody RegistrationVO registrationVO) {
        log.info("Registering Customer: " + registrationVO);
        var customer = customerService.registerCustomer(registrationVO);

        return ResponseEntity.ok(ApiResponseVO.<String>builder()
                .message("Customer saved successfully")
                .time(String.valueOf(Instant.now())).payload(customer.getPayload().toString())
                .build());
    }

    @Operation(
            summary = "This is an endpoint for finding a customer by email."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer registered",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerResponseVO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid email address"),
            @ApiResponse(responseCode = "403", description = "Invalid / expired token"),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)

    })
    @SneakyThrows
    @GetMapping
    public ResponseEntity<ApiResponseVO<CustomerResponseVO>> getCustomerByEmail(@Validated @RequestBody CustomerEmailRequestVO customerEmailRequestVO)  {
        log.info("Getting Customer by email: " + customerEmailRequestVO);
        CustomerResponseVO customer = customerService.findCustomerByEmail(customerEmailRequestVO.getEmail());

        return ResponseEntity.ok(ApiResponseVO.<CustomerResponseVO>builder()
                .payload(customer)
                .build());
    }
}
