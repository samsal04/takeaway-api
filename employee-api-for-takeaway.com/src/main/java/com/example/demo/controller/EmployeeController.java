package com.example.demo.controller;

import com.example.demo.dao.Employee;
import com.example.demo.service.EmployeeService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // POST/employees/create
    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){

      Employee createdEmployee= employeeService.creatEmployee(employee);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    // GET /employees endpoint
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees= employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // GET /employees/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id){
        Employee employee= employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    // PUT /employees/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee updateEmployee){
        Employee employee= employeeService.upadateEmployee(id,updateEmployee);
        return ResponseEntity.ok(employee);
    }

    // DELETE /employees/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }


}
