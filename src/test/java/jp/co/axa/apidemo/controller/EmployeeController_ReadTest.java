package jp.co.axa.apidemo.controller;

import jp.co.axa.apidemo.entities.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeController_ReadTest extends BaseTest {
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

    @Test
    public void testGetEmployee() throws Exception {
        Employee foo = new Employee("foo", 1000, "it");
        foo.setId(888l);
        when(employeeRepository.findById(888l)).thenReturn(Optional.of(foo));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/888"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(foo))
                ;
        verify(employeeRepository).findById(888l);
    }

    @Test
    public void testGetEmployee_NotFound() throws Exception {
        when(employeeRepository.findById(888l)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/888"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("The specified employee{id=888} not exist."));
                ;
        verify(employeeRepository).findById(888l);
    }
}
