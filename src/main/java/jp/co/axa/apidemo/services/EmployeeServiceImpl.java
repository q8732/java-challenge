package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Cacheable(value = "list")
    public List<Employee> retrieveEmployees() {
        return employeeRepository.findAll();
    }

    @Cacheable("employee")
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        return optEmp.orElseThrow(
                () -> new NoSuchElementException(String.format("The specified employee{id=%d} not exist.", employeeId))
        );
    }

    @CachePut(value = "employee", key = "#employee.getId()")
    @CacheEvict(value = "list", allEntries = true)
    @Transactional
    public Employee saveEmployee(Employee employee){
        employeeRepository.save(employee);
        return employee;
    }

    @Caching(evict = {@CacheEvict({"employee"}), @CacheEvict(value = "list", allEntries = true)})
    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    @CachePut(value = "employee", key = "#employee.getId()")
    @CacheEvict(value = "list", allEntries = true)
    @Transactional
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public boolean existsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }

    @Override
    public List<Employee> retrievePage(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }
}