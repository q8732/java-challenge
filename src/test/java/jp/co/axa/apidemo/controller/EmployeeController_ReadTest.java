package jp.co.axa.apidemo.controller;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeController_ReadTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    public void testGetEmployees() throws Exception {
        Employee[] employees = {
                new Employee("foo", 1000, "hr"),
                new Employee("bar", 2000, "it")
        };
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employees));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].name").value(employees[0].getName()))
                .andExpect(jsonPath("$.[1].name").value(employees[1].getName()))
                ;
        verify(employeeRepository).findAll();
    }

    @Test
    public void testGetEmployeesWithEmpty() throws Exception {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
        ;
        verify(employeeRepository).findAll();
    }
}
