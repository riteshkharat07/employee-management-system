package com.ritesh.employeeservice.service;

import com.ritesh.employeeservice.dto.EmployeeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeByID(Long id);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);

    List<EmployeeDTO> searchEmployees(String keyword);

    Page<EmployeeDTO> getAllEmployees(int page, int size);  //pagination

    List<EmployeeDTO> sortEmployees(String sortBy,String direction);   //Sorting
}