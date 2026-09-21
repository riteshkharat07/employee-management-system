package com.ritesh.employeeservice.controller;

import com.ritesh.employeeservice.dto.EmployeeDTO;
import com.ritesh.employeeservice.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/page")  //Pagination
    public Page<EmployeeDTO> getAllEmployeesPage(@RequestParam int page,
                                                 @RequestParam int size) {

        return employeeService.getAllEmployees(page, size);
    }

    @GetMapping("/sort")
    public List<EmployeeDTO> sortEmployees(
            @RequestParam String sortBy,
            @RequestParam String direction) {

        return employeeService.sortEmployees(sortBy, direction);
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployee(@Valid @PathVariable Long id) {
        return employeeService.getEmployeeByID(id);
    }

    @GetMapping("/search")
    public List<EmployeeDTO> searchEmployees(
            @RequestParam String keyword) {

        return employeeService.searchEmployees(keyword);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, @PathVariable Long id) {
        return employeeService.updateEmployee(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@Valid @PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

}
