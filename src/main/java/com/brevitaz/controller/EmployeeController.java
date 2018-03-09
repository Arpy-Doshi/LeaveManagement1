package com.brevitaz.controller;

import com.brevitaz.dao.EmployeeDao;
import com.brevitaz.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(method = RequestMethod.POST)
    public boolean create(@RequestBody Employee employee) {
        return employeeDao.create(employee);

    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Employee> getAll()  {
        return employeeDao.getAll();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public boolean update(@RequestBody Employee employee, @PathVariable String id){
        return employeeDao.update(employee,id);

    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    public boolean delete(@PathVariable String id){
        return employeeDao.delete(id);

    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public Employee getById(@PathVariable String id){
        return employeeDao.getById(id);

    }
}