package com.brevitaz.service.impl;

import com.brevitaz.dao.LeaveApplicationDao;
import com.brevitaz.model.LeaveApplication;
import com.brevitaz.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService
{
    @Autowired
    private LeaveApplicationDao leaveApplicationDao;


    @Override
    public List<LeaveApplication> getByDate(Date fromDate, Date toDate) {

        List<LeaveApplication> leaveApplications1;
        List<LeaveApplication> leaveApplications2 = new ArrayList<>();

        if (fromDate == null || toDate == null )
            return null;
        else
           leaveApplications1= leaveApplicationDao.getAll();

        for (LeaveApplication leaveapplication2:leaveApplications1)
        {
            if (fromDate.compareTo(leaveapplication2.getFromDate()) == 0)
                leaveApplications2.add(leaveapplication2);
            if (toDate.compareTo(leaveapplication2.getFromDate()) == 0)
                leaveApplications2.add(leaveapplication2);
            if (leaveapplication2.getFromDate().compareTo(fromDate) == 1 && leaveapplication2.getFromDate().compareTo(toDate) == -1 )
                leaveApplications2.add(leaveapplication2);
        }
        
        return leaveApplications2;


    }
}
