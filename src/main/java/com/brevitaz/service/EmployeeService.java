package com.brevitaz.service;

import com.brevitaz.model.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService
{
    public ResponseEntity<String> create (Employee employee);
    public ResponseEntity<String> delete (String id);
    public ResponseEntity<String> update(Employee employee,String id);
    public Employee getById (String id);
    public List<Employee> getAll();
}
