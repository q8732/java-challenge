package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * A REST controller provides CRUD operations on {@link Employee} by supported HTTP methods.
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Value("${page.size}")
    int DEFAULT_PAGE_SIZE;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmployeeService employeeService;

    /**
     * Get a list of available employees, with optional pagination.
     * @param page zero-indexed page number
     * @param pageSize {@link #DEFAULT_PAGE_SIZE} is used if absent
     */
    @GetMapping("/employees")
    public List<Employee> getEmployees(Optional<Integer> page, Optional<Integer> pageSize) {
        if (page.isPresent()) {
            return employeeService.retrievePage(page.get(), pageSize.orElse(DEFAULT_PAGE_SIZE));
        }
        return employeeService.retrieveEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody @Validated Employee employee, BindingResult bindingResult)
            throws BindException {
        if (employee.getId() != null) {
            bindingResult.rejectValue("id", "", "must not be set");
        }
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        logger.info("Employee Saved Successfully");
        return employeeService.saveEmployee(employee);
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        if (!employeeService.existsById(employeeId)) {
            throw new NoSuchElementException(String.format("The specified employee{id=%d} does not exist.", employeeId));
        }
        employeeService.deleteEmployee(employeeId);
        logger.info("Employee Deleted Successfully");
    }

    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody @Validated Employee employee,
                               BindingResult bindingResult,
                               @PathVariable(name="employeeId")Long employeeId) throws BindException {
        ValidationUtils.rejectIfEmpty(bindingResult, "id", "", "must be set");
        if (employee.getId() != null && !employee.getId().equals(employeeId)) {
            bindingResult.rejectValue("id", "",
                    String.format("ids{%d, %d} are not matched",employeeId, employee.getId()));
        }
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        if (employeeService.existsById(employeeId)) {
            employeeService.updateEmployee(employee);
        }
    }

}
