package com.shopwell.api.service;

import com.shopwell.api.model.DTOs.request.EmployeeRegistrationVO;
import com.shopwell.api.model.DTOs.response.ApiResponseVO;

public interface EmployeeService {

    void registerEmployee(EmployeeRegistrationVO registrationVO);
}
