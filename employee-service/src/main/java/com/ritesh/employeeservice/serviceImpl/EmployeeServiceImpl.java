package com.ritesh.employeeservice.serviceImpl;

import com.ritesh.employeeservice.dto.EmployeeDTO;
import com.ritesh.employeeservice.entity.Employee;
import com.ritesh.employeeservice.event.EmployeeEvent;
import com.ritesh.employeeservice.event.EmployeeProducer;
import com.ritesh.employeeservice.exception.EmployeeNotFoundException;
import com.ritesh.employeeservice.repository.EmployeeRepository;
import com.ritesh.employeeservice.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeProducer employeeProducer;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            EmployeeProducer employeeProducer) {

        this.employeeRepository = employeeRepository;
        this.employeeProducer = employeeProducer;
    }

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        logger.info("Creating employee: {}", employeeDTO.getFirstName());

        Employee employee = new Employee();

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhone(employeeDTO.getPhone());
        employee.setDesignation(employeeDTO.getDesignation());
        employee.setSalary(employeeDTO.getSalary());

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeEvent event = new EmployeeEvent(
                savedEmployee.getId(),
                savedEmployee.getFirstName(),
                savedEmployee.getLastName(),
                savedEmployee.getEmail(),
                savedEmployee.getDesignation()
        );

        employeeProducer.sendEmployeeEvent(event);

        return convertToDTO(savedEmployee);
    }

    @Override
    @Cacheable(value = "employees", key = "#id")
    public EmployeeDTO getEmployeeByID(Long id) {
        logger.info("Fetching employee: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        return convertToDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {

        logger.info("Updating employee: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhone(employeeDTO.getPhone());
        employee.setDesignation(employeeDTO.getDesignation());
        employee.setSalary(employeeDTO.getSalary());

        Employee updatedEmployee = employeeRepository.save(employee);

        return convertToDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {

        logger.info("Deleting employee: {}", id);

        employeeRepository.deleteById(id);
    }
    @Override
    public List<EmployeeDTO> searchEmployees(String keyword) {

        List<Employee> searchEmployees =
                employeeRepository
                        .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                                keyword,
                                keyword
                        );

        return searchEmployees.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override //Pagination
    public Page<EmployeeDTO> getAllEmployees(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        return employeeRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override  //Sorting
    public List<EmployeeDTO> sortEmployees(String sortBy, String direction) {

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        return employeeRepository.findAll(sort)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private EmployeeDTO convertToDTO(Employee employee) {

        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setDesignation(employee.getDesignation());
        dto.setSalary(employee.getSalary());

        return dto;
    }

}