package com.ritesh.ems.serviceImpl;
import com.ritesh.ems.entity.Employee;
import com.ritesh.ems.repository.EmployeeRepository;
import com.ritesh.ems.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeByID(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if (existingEmployee == null) {
            return null;
        }
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setDesignation(employee.getDesignation());
        existingEmployee.setDateofJoining(employee.getDateofJoining());
        existingEmployee.setStatus(employee.getStatus());
        return employeeRepository.save(existingEmployee);

    }

    @Override
    public void deleteEmployee(Long id) {
    employeeRepository.deleteById(id);
    }
}
