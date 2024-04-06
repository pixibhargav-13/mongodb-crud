package com.bhargav.mongodbcrud.service.impl;

import com.bhargav.mongodbcrud.Repository.EmployeeRepository;
import com.bhargav.mongodbcrud.model.Employee;
import com.bhargav.mongodbcrud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee createEmployee(Employee employee) {
        mongoTemplate.indexOps(Employee.class)
                .ensureIndex(new Index().on("createdAt", Sort.Direction.ASC).expire(40));

        mongoTemplate.indexOps(Employee.class)
                .ensureIndex(new Index().on("email", Sort.Direction.ASC).unique());

        employee.populateCreatedAt();
        return mongoTemplate.save(employee);
    }

    public Employee updateEmployee(String id, Employee employee) {
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }
}
