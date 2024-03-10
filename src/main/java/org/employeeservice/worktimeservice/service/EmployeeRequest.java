package org.employeeservice.worktimeservice.service;

import org.employeeservice.worktimeservice.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeRequest {

        private final MongoRepository repository;
        private String employeeId;

    public EmployeeRequest(MongoRepository repository) {
        this.repository = repository;
    }

    public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }


    }
