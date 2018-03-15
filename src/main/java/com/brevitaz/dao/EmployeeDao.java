package com.brevitaz.dao;

import com.brevitaz.model.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeDao
{
    public boolean create(Employee employee);
    public List<Employee> getAll() ;
    public ResponseEntity<String> update(Employee employee,String id);
    public ResponseEntity<String> delete(String id);
    public Employee getById(String id);
}
