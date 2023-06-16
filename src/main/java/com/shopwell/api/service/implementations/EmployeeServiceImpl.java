package com.shopwell.api.service.implementations;

import com.shopwell.api.model.DTOs.request.EmployeeRegistrationVO;
import com.shopwell.api.model.entity.Employee;
import com.shopwell.api.repository.EmployeeRepository;
import com.shopwell.api.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public void registerEmployee(EmployeeRegistrationVO registrationVO) {
        employeeRepository.save(mapEmployeeRegistrationVO(registrationVO));
    }

    private Employee mapEmployeeRegistrationVO(EmployeeRegistrationVO registration) {
        return Employee.builder()
                .employeeFirstName(registration.getEmployeeFirstName())
                .employeeLastName(registration.getEmployeeLastName())
                .employeeEmail(registration.getEmployeeEmail())
                .employeeDateOfBirth(Timestamp.valueOf(registration.getEmployeeDateOfBirth()))
                .employeeCity(registration.getEmployeeCity())
                .employeeStreetAddress(registration.getEmployeeStreetAddress())
                .employeePhoneNumber(registration.getEmployeePhoneNumber())
                .build();
    }
}
