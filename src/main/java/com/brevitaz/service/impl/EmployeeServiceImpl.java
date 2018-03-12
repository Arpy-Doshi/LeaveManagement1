package com.brevitaz.service.impl;

import com.brevitaz.dao.EmployeeDao;
import com.brevitaz.model.Employee;
import com.brevitaz.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService
{

    @Autowired
    private EmployeeDao employeeDao;


    @Override
    public ResponseEntity<String> create(Employee employee) {
        if(employee.getId().trim().length() <= 0|| employee.getName().trim().length()<=0)
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);

        //Employee employee = employeeService.getByUsernameAndPassword(username,password);

        if(employee!=null)
            return employeeDao.create(employee);
        else
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(Employee employee, String id) {
        return false;
    }

    @Override
    public Employee getById(String id) {
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }
}
