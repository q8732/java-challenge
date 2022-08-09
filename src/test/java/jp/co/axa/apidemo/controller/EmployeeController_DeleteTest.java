package jp.co.axa.apidemo.controller;

import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeController_DeleteTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    public void testDeleteEmployee() throws Exception {
        when(employeeRepository.existsById(1l)).thenReturn(true);
        this.mockMvc.perform(delete("/api/v1/employees/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
        ;
        verify(employeeRepository).deleteById(1l);
    }

    @Test
    public void testDeleteEmployeeNotExist() throws Exception {
        when(employeeRepository.existsById(1l)).thenReturn(false);
        this.mockMvc.perform(delete("/api/v1/employees/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(CoreMatchers.containsString("does not exist")))
        ;
        verify(employeeRepository, never()).deleteById(any());
    }
}
