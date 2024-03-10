package org.employeeservice.worktimeservice.DAO;

import org.employeeservice.worktimeservice.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository <Employee, String> {

}
