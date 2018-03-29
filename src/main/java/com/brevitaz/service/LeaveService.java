package com.brevitaz.service;

import com.brevitaz.model.LeaveApplication;

import java.util.List;

public interface LeaveService
{
    public double checkBalance(String employeeId);

    public List<LeaveApplication>getByEmployeeId(String employeeId);

}
