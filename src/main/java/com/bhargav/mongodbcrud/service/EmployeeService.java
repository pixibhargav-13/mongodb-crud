package com.bhargav.mongodbcrud.service;

import com.bhargav.mongodbcrud.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployeeById(String id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(String id, Employee employee);

    void deleteEmployee(String id);
}
