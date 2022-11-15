package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();

    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) throws IllegalAccessException {
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalAccessException("Введите имя и фамилию");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());

        this.employees.put(employee.getId(), employee);
        return employee;

    }
    public int getSalarySum(){
        return employees
                .values()
                .stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }
    public int getMinSalary(){
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .min().orElse(0);
    }
    public int getMaxSalary(){
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .max().orElse(0);
    }
    public List<Employee> getEmployeesAboveAverageSalary(){
        var averageSalary = employees.values().stream().mapToInt(Employee::getSalary)
                .average().orElseThrow();
        return employees.values().stream().filter(employee ->
                averageSalary < employee.getSalary()).collect(Collectors.toList());
    }
}
