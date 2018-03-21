package com.brevitaz.service.impl;

import com.brevitaz.dao.LeaveApplicationDao;
import com.brevitaz.model.LeaveApplication;
import com.brevitaz.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LeaveServiceImpl implements LeaveService
{

    @Autowired
    private LeaveApplicationDao leaveApplicationDao;

    @Override
    public double checkBalance(String employeeId) {
        return 0;
    }

    @Override
    public List<LeaveApplication> getByEmployeeId(String employeeId)
    {
        leaveApplicationDao.getByEmployeeId(employeeId);

        return leaveApplicationDao.getByEmployeeId(employeeId);
    }
/*
        List<LeaveApplication> leaveApplications = null;

        if (employeeId.trim().length()<=0)
            throw new RuntimeException("Id is null!!!");
        else if (employeeId.trim().length() > 0) {
            leaveApplications =
 return leaveApplicationDao.getByEmployeeId(employeeId);
  return leaveApplications;
        }
        else
            throw new RuntimeException("Bad Request!!!!");


    }
*/

}
