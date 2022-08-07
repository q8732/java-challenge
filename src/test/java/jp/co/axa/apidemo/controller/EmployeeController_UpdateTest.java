package jp.co.axa.apidemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeController_UpdateTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    EmployeeRepository employeeRepository;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee foo = new Employee("foo", 1, "hr");
        foo.setId(888l);
        when(employeeRepository.existsById(foo.getId())).thenReturn(true);
        this.mockMvc.perform(put("/api/v1/employees/888")
                        .content("{\"id\":888, \"name\": \"foo\", \"salary\":1000, \"department\":\"it\"}")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
        ;
        foo.setDepartment("it");
        foo.setSalary(1000);
        verify(employeeRepository).save(foo);
    }

    @Test
    public void testUpdateEmployeeWithUnmatchedIds() throws Exception {
        Employee foo = new Employee("foo", 1, "it");
        foo.setId(888l);
        this.mockMvc.perform(put("/api/v1/employees/777")
                        .content(mapper.writeValueAsString(foo))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0]").value(containsString("are not matched")))
        ;
        verify(employeeRepository, never()).save(any());
    }

    @Test
    public void testUpdateEmployeeWithValidationErrors() throws Exception {
        Employee foo = new Employee("foo", -1000, "");
        foo.setId(888l);
        this.mockMvc.perform(put("/api/v1/employees/888")
                        .content(mapper.writeValueAsString(foo))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(hasItem(containsString("must not be blank"))))
                .andExpect(jsonPath("$.errors").value(hasItem(containsString("must be greater than or equal to 0"))))
        ;
        verify(employeeRepository, never()).save(any());
    }
}
