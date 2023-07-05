package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.CustomerEmailRequestVO;
import com.shopwell.api.model.VOs.request.CustomerRegistrationVO;
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
            content = { @Content(mediaType = "multipart/form-data")}),
            @ApiResponse(responseCode = "401", description = "Invalid / malformed request"),
            @ApiResponse(responseCode = "403", description = "Invalid / expired token"),
    })
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseVO<String>> registerCustomer(@Validated @ModelAttribute CustomerRegistrationVO customerRegistrationVO) {
        log.info("Registering Customer: " + customerRegistrationVO);
        customerService.registerCustomer(customerRegistrationVO);

        return ResponseEntity.ok(ApiResponseVO.<String>builder()
                .message("Customer saved successfully")
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
