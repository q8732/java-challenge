package jp.co.axa.apidemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * A base class to provide a default test environment.
 * <li>disabled caching</li>
 * <li>without database</li>
 */
@SpringBootTest(properties = {"spring.cache.type=none",
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"})
@AutoConfigureMockMvc
public class NoDatabaseTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    EmployeeRepository employeeRepository;
    ObjectMapper mapper = new ObjectMapper();
}
