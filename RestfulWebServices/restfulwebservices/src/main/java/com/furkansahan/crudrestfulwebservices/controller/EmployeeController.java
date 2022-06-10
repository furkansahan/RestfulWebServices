package com.furkansahan.crudrestfulwebservices.controller;

import com.furkansahan.crudrestfulwebservices.exception.ResourceNotFoundException;
import com.furkansahan.crudrestfulwebservices.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.furkansahan.crudrestfulwebservices.repository.EmployeeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    //create get all employees api
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    //create employe
    @PostMapping("/employees")
    public Employee createEmployee(@Validated  @RequestBody Employee employee ){
        return employeeRepository.save(employee);
    }

    //get employee by id
    @GetMapping("employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id")long employeeId
                                     ) throws ResourceNotFoundException{
        Employee employee= employeeRepository.findById(employeeId)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found for this id : " + employeeId));
        return ResponseEntity.ok().body(employee);
    }
    //update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id")long employeeId, @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
        Employee employee= employeeRepository.findById(employeeId)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found for this id : " + employeeId));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setFirstName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        employeeRepository.save(employee);

        return ResponseEntity.ok().body(employee);
    }
    //delete employee by id
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id")long employeeId) throws ResourceNotFoundException {
         employeeRepository.findById(employeeId)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found for this id : " + employeeId));
        employeeRepository.deleteById(employeeId);
        return ResponseEntity.ok().build();
    }
}
