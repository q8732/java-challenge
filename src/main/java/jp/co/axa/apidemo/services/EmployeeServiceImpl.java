package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> retrieveEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        return optEmp.orElseThrow(
                () -> new NoSuchElementException(String.format("The specified employee{id=%d} not exist.", employeeId))
        );
    }

    public Employee saveEmployee(Employee employee){
        employeeRepository.save(employee);
        return employee;
    }

    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public boolean existsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}