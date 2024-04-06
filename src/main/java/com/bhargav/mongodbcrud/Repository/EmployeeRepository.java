package com.bhargav.mongodbcrud.Repository;

import com.bhargav.mongodbcrud.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {}
