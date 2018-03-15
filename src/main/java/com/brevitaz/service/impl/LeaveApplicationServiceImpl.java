package com.brevitaz.service.impl;

import com.brevitaz.dao.EmployeeDao;
import com.brevitaz.dao.LeaveApplicationDao;
import com.brevitaz.model.Employee;
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

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public boolean request(LeaveApplication leaveApplication) {

        Employee employee = employeeDao.getById(leaveApplication.getEmployeeId());

        if(leaveApplication.getEmployeeId().trim().length() >0 && employee.getName().trim().length()>0)
        {
            if(employee.getName().equals(leaveApplication.getEmployeeName()))
            {
                Date date = new Date();
                if (leaveApplication.getFromDate().compareTo(date) == 1 && leaveApplication.getToDate().compareTo(date) == 1 && leaveApplication.getFromDate().compareTo(leaveApplication.getToDate()) == -1)
                {
                    return leaveApplicationDao.request(leaveApplication);
                }
            }
        }
        else
        {
            return false;
        }
         return false;
    }

    @Override
    public boolean cancelRequest(String id) {

        return leaveApplicationDao.cancelRequest(id);
    }

    @Override
    public boolean updateRequest(LeaveApplication leaveApplication, String id) {
        return leaveApplicationDao.updateRequest(leaveApplication,id);
    }

    @Override
    public LeaveApplication getById(String id) {
        return leaveApplicationDao.getById(id);
    }

    @Override
    public List<LeaveApplication> checkRequest() {
        return leaveApplicationDao.checkRequest();
    }

    @Override
    public boolean approveRequest(String id) {
        return leaveApplicationDao.approveRequest(id);
    }

    @Override
    public boolean declineRequest(String id) {
        return leaveApplicationDao.declineRequest(id);
    }

    @Override
    public List<LeaveApplication> getAll() {
        return leaveApplicationDao.getAll();
    }

    @Override
    public List<LeaveApplication> getByDate(Date fromDate, Date toDate) {

        List<LeaveApplication> leaveApplications1;
        List<LeaveApplication> leaveApplications2 = new ArrayList<>();

       /* if (fromDate == null || toDate == null )
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
        */
            leaveApplications1 = leaveApplicationDao.getAll();

            for(LeaveApplication application : leaveApplications1)
            {
                if (fromDate.compareTo(application.getFromDate()) == 0)
                    leaveApplications2.add(application);

                else if(toDate.compareTo(application.getFromDate()) == 0)
                {
                    leaveApplications2.add(application);
                }
                else if(application.getFromDate().compareTo(fromDate) == 1 && application.getFromDate().compareTo(toDate) == -1)
                {
                    leaveApplications2.add(application);
                }
            }
        return leaveApplications2;
    }
}
