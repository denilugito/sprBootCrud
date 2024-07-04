package com.example.lugito.service;

import com.example.lugito.model.Employee;
import com.example.lugito.repository.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        Optional<Employee> optionalEmployee = employeeRepo.findById(id);
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        }
        log.info("Employee with id: {} doesn't exist", id);
        return null;
    }

    public Employee saveEmployee(Employee employee) {
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        Employee savedEmployee = employeeRepo.save(employee);

        log.info("Employee with Id: {} saved successfully", employee.getId());
        return savedEmployee;
    }

    public Employee updateEmployee(Employee employee) throws Exception {
        Optional<Employee> existingEmployee = employeeRepo.findById(employee.getId());

        if (existingEmployee.isPresent()) {
            employee.setCreatedAt(existingEmployee.get().getCreatedAt());
            employee.setUpdatedAt(LocalDateTime.now());

            Employee updatedEmployee = employeeRepo.save(employee);
            return updatedEmployee;
        } else {
            log.error("Update Error, There is No Existing Employee With Id: {}", employee.getId());
            throw new Exception("Update Error, There is No Existing Employee With Id: " + employee.getId());
        }
    }

    public void deleteEmployeeById(Integer id) throws Exception {
        Optional<Employee> existingEmployee = employeeRepo.findById(id);

        if (existingEmployee.isPresent()) {
            employeeRepo.deleteById(id);
        } else {
            log.error("There is No Existing Employee With Id: {}", id);
            throw new Exception("There is No Existing Employee With Id: " + id);
        }
    }
}
