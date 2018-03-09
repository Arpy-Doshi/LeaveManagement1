package com.brevitaz.controller;


import com.brevitaz.dao.LeaveApplicationDao;
import com.brevitaz.model.LeaveApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaves")
public class LeaveController
{
    @Autowired
    LeaveApplicationDao leaveApplicationDao;

    @RequestMapping(value = "/employee/{employeeId}/balance" , method = RequestMethod.GET)//remaining
    public double checkBalance(@PathVariable String employeeId)
    {
        return 0;
    }// TODO: it should be in leave controller

    @RequestMapping(value = "/employee/{employeeId}" , method = RequestMethod.GET) // personal record.
    public List<LeaveApplication> getByEmployeeId(@PathVariable String employeeId)  {
        return leaveApplicationDao.getByEmployeeId(employeeId);// TODO:should be in employee controller
    }

}
