package jp.co.axa.apidemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeController_CreateTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    EmployeeRepository employeeRepository;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSaveEmployee() throws Exception {
        Employee foo = new Employee("foo", 10000, "it");
        when(employeeRepository.save(any(Employee.class))).thenReturn(foo);
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(foo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(foo.getName()))
                .andExpect(jsonPath("$.department").value(foo.getDepartment()))
                .andExpect(jsonPath("$.salary").value(foo.getSalary()))
                ;
        verify(employeeRepository).save(any());
    }

    @Test
    public void testSaveEmployeeWithIdError() throws Exception {
        Employee foo = new Employee("foo", 10000, "it");
        foo.setId(888l);
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(foo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors").value(Matchers.hasItem("id : must not be set")))
                ;
        verify(employeeRepository, never()).save(any());
    }

    @Test
    public void testSaveEmployeeWithValidationErrors() throws Exception {
        Employee foo = new Employee("", -1, "   ");
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(foo))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors").value(Matchers.hasItems("name : must not be blank",
                        "department : must not be blank","salary : must be greater than or equal to 0")))
                ;
        verify(employeeRepository, never()).save(any());
    }
}
