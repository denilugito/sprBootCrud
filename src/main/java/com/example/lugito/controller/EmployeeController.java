package com.example.lugito.controller;

import com.example.lugito.model.Employee;
import com.example.lugito.service.EmployeeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok().body(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(employeeService.getEmployeeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok().body(employeeService.saveEmployee(employee));
    }

    @PutMapping("/")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee updatedEmployeeRes = new Employee();

        try {
            updatedEmployeeRes = employeeService.updateEmployee(employee);
            return ResponseEntity.ok().body(updatedEmployeeRes);
        } catch (Exception e) {
            updatedEmployeeRes.setHttpErrCd(HttpStatus.INTERNAL_SERVER_ERROR.value());
            updatedEmployeeRes.setHttpErrMsg(e.getMessage());
            return new ResponseEntity<>(updatedEmployeeRes, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Integer id) {
        try {
            employeeService.deleteEmployeeById(id);
            return ResponseEntity.ok().body("Employe Deleted Successfully With Id: " + id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
