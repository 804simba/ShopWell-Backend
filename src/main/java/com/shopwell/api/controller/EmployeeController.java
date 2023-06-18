package com.shopwell.api.controller;

import com.shopwell.api.model.VOs.request.BrandRegistrationVO;
import com.shopwell.api.model.VOs.request.EmployeeRegistrationVO;
import com.shopwell.api.model.VOs.response.ApiResponseVO;
import com.shopwell.api.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponseVO<String>> registerEmployee(@RequestBody EmployeeRegistrationVO employeeRegistrationVO) {
        log.info(String.format("Registering Employee %s: ", employeeRegistrationVO.toString()));
        employeeService.registerEmployee(employeeRegistrationVO);

        return ResponseEntity.ok(ApiResponseVO.<String>builder()
                .message("Employee saved successfully")
                .build());
    }
}
