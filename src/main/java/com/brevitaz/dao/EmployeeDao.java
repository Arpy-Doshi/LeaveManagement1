package com.brevitaz.dao;

import com.brevitaz.model.Employee;

import java.util.List;

public interface EmployeeDao
{
    public boolean create(Employee employee);
    public List<Employee> getAll() ;
    public boolean update(Employee employee,String id);
    public boolean delete(String id);
    public Employee getById(String id);
}
