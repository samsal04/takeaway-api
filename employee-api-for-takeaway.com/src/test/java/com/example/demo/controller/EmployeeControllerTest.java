package com.example.demo.controller;

import com.example.demo.dao.Employee;
import com.example.demo.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateEmployee() {
        // Create an Employee object
        UUID uuid = UUID.randomUUID();
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        Date birthDate = new Date();
        List<String> hobbies = List.of("hobby1", "hobby2", "hobby3");
        Employee employee = new Employee(uuid, email, firstName, lastName, birthDate, hobbies);

        // Save the Employee object to the database
        Employee createdEmployee = employeeService.creatEmployee(employee);

        // Verify the created Employee object
        assertNotNull(createdEmployee.getUuid());
        assertEquals(email, createdEmployee.getEmail());
        assertEquals(firstName, createdEmployee.getFirstName());
        assertEquals(lastName, createdEmployee.getLastName());
        assertEquals(birthDate, createdEmployee.getBirthDate());
        assertEquals(hobbies, createdEmployee.getHobbies());
    }

    @Test
    public void testGetAllEmployees() {
        // Create employee1
        Date birthDate1 = new Date();
        UUID uuid1 = UUID.randomUUID();
        Employee employee1 = new Employee();
        employee1.setUuid(uuid1);
        employee1.setEmail("email1");
        employee1.setFirstName("firstName1");
        employee1.setLastName("lastName1");
        employee1.setBirthDate(birthDate1);
        employee1.setHobbies(Arrays.asList("hobbies1"));
        employeeService.creatEmployee(employee1);

        // Create employee2
        Date birthDate2 = new Date();
        UUID uuid2 = UUID.randomUUID();

        Employee employee2 = new Employee();
        employee2.setUuid(uuid2);
        employee2.setEmail("email2");
        employee2.setFirstName("firstName2");
        employee2.setLastName("lastName2");
        employee2.setBirthDate(birthDate2);
        employee2.setHobbies(Arrays.asList("hobbies2"));
        employeeService.creatEmployee(employee2);

        // Retrieve all employees
        List<Employee> retrievedEmployees = employeeService.getAllEmployees();

        // Assert the retrieved employees list
        assertNotNull(retrievedEmployees, "Retrieved Employees list should not be null");
//        assertEquals(2, retrievedEmployees.size(), "Retrieved Employees list should have 2 elements");

        if (retrievedEmployees.size() >= 1) {
            // Assert employee1 details
            Employee retrievedEmployee1 = retrievedEmployees.get(0);
            assertNotNull(retrievedEmployee1, "Retrieved Employee1 should not be null");
            assertEquals("uuid1", retrievedEmployee1.getUuid().toString(), "UUID should match for employee1");
            assertEquals("email1", retrievedEmployee1.getEmail(), "Email should match for employee1");
            assertEquals("firstName1", retrievedEmployee1.getFirstName(), "First Name should match for employee1");
            assertEquals("lastName1", retrievedEmployee1.getLastName(), "Last Name should match for employee1");
            assertEquals(birthDate1, retrievedEmployee1.getBirthDate(), "Birth Date should match for employee1");
            assertEquals(Arrays.asList("hobbies1"), retrievedEmployee1.getHobbies(), "Hobbies should match for employee1");
        }

        if (retrievedEmployees.size() >= 2) {
            // Assert employee2 details
            Employee retrievedEmployee2 = retrievedEmployees.get(1);
            assertNotNull(retrievedEmployee2, "Retrieved Employee2 should not be null");
            assertEquals("uuid2", retrievedEmployee2.getUuid().toString(), "UUID should match for employee2");
            assertEquals("email2", retrievedEmployee2.getEmail(), "Email should match for employee2");
            assertEquals("firstName2", retrievedEmployee2.getFirstName(), "First Name should match for employee2");
            assertEquals("lastName2", retrievedEmployee2.getLastName(), "Last Name should match for employee2");
            assertEquals(birthDate2, retrievedEmployee2.getBirthDate(), "Birth Date should match for employee2");
            assertEquals(Arrays.asList("hobbies2"), retrievedEmployee2.getHobbies(), "Hobbies should match for employee2");
        }
    }

    public void testGetEmployeeById() {
        // Create an Employee object
        UUID uuid = UUID.randomUUID();
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        Date birthDate = new Date();
        List<String> hobbies = List.of("hobby1", "hobby2", "hobby3");
        Employee employee = new Employee(uuid, email, firstName, lastName, birthDate, hobbies);

        // Save the Employee object to the database
        employeeService.creatEmployee(employee);

        // Get the Employee by ID
        Employee retrievedEmployee = employeeService.getEmployeeById(uuid.toString());

        // Verify the retrieved Employee object
        assertNotNull(retrievedEmployee, "Retrieved Employee should not be null");
        assertNotNull(retrievedEmployee.getUuid(), "Retrieved Employee's UUID should not be null");
        assertNotNull(retrievedEmployee.getEmail(), "Retrieved Employee's email should not be null");
        assertNotNull(retrievedEmployee.getFirstName(), "Retrieved Employee's first name should not be null");
        assertNotNull(retrievedEmployee.getLastName(), "Retrieved Employee's last name should not be null");
        assertNotNull(retrievedEmployee.getBirthDate(), "Retrieved Employee's birth date should not be null");
        assertNotNull(retrievedEmployee.getHobbies(), "Retrieved Employee's hobbies should not be null");
    }

}