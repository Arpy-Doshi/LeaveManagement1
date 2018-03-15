package com.brevitaz.controller;

import com.brevitaz.model.Employee;
import com.brevitaz.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.POST)
    public boolean create(@RequestBody Employee employee) {
        return employeeService.create(employee);

    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Employee> getAll()  {
        return employeeService.getAll();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@RequestBody Employee employee, @PathVariable String id){
        return employeeService.update(employee,id);

    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    public ResponseEntity<String> delete(@PathVariable String id){
        System.out.println("controller is called.");
        return employeeService.delete(id);

    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public Employee getById(@PathVariable String id){
        return employeeService.getById(id);

    }
}