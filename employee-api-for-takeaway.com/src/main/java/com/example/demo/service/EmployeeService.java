package com.example.demo.service;

import com.example.demo.dao.Employee;

import com.example.demo.repository.EmployeeRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private KafkaTemplate<String,Employee> kafkaTemplate;


    public Employee creatEmployee(Employee employee) {
        // check if email already exists
       Optional<Employee> optionalEmployee= employeeRepository.findByEmail(employee.getEmail());
        if (optionalEmployee.isPresent()) {
            throw new IllegalArgumentException("Employee with email already exists.");
        }
        // save employee to database
        Employee createdEmployee=employeeRepository.save(employee);
        // publish event to kafka
        kafkaTemplate.send("employee-created",createdEmployee);
        return createdEmployee;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(String id) {
        Optional<Employee> optionalEmployee= employeeRepository.findById(UUID.fromString(id));
        if (optionalEmployee.isEmpty()){
            throw new NoSuchElementException("Employee not found exception");
        }
        System.out.println(" this is getEmployee");
        return optionalEmployee.get();
    }

    public Employee upadateEmployee(String id, Employee updateEmployee) {

        Optional<Employee> optionalEmployee= employeeRepository.findById(UUID.fromString(id));
        if(optionalEmployee.isEmpty()){
            throw new NoSuchElementException("Employee not found");
        }
        Employee employee= optionalEmployee.get();

        employee.setEmail(updateEmployee.getEmail());
        employee.setFirstName(updateEmployee.getFirstName());
        employee.setLastName(updateEmployee.getLastName());
        employee.setBirthDate(updateEmployee.getBirthDate());
        employee.setHobbies(updateEmployee.getHobbies());

        // save updated employee to the database
        Employee updatedEmployeeInDB= employeeRepository.save(employee);

        // publish in kafka
        kafkaTemplate.send("employee-updated",updatedEmployeeInDB);

        return updatedEmployeeInDB;
    }

    public void deleteEmployee(String id) {
        Optional<Employee> optionalEmployee =employeeRepository.findById(UUID.fromString(id));
        if(optionalEmployee.isEmpty()){
            throw new NoSuchElementException("Employee not found");
        }

        employeeRepository.deleteById(UUID.fromString(id));
    }
}
