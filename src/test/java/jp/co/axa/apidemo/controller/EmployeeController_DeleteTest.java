package jp.co.axa.apidemo.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeController_DeleteTest extends NoDatabaseTest {
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
