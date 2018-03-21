package com.brevitaz.service.impl;

import com.brevitaz.dao.EmployeeDao;
import com.brevitaz.dao.LeaveApplicationDao;
import com.brevitaz.errors.*;
import com.brevitaz.model.Employee;
import com.brevitaz.model.LeaveApplication;
import com.brevitaz.model.Status;
import com.brevitaz.model.Type;
import com.brevitaz.service.LeaveApplicationService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private RestHighLevelClient client;

    @Value("${LeaveApplication-Index-Name}")
    private String indexName;


    @Override
    public boolean request(LeaveApplication leaveApplication) {

        Date date = new Date();
        if (leaveApplication.getFromDate().compareTo(date) == -1)
        {
            throw new InvalidDateException("From date is invalid");
        }
        if(leaveApplication.getToDate().compareTo(date) == -1)
        {
            throw new InvalidDateException("To date is invalid");
        }
        if(leaveApplication.getFromDate().compareTo(leaveApplication.getToDate()) == 1)
        {
            throw new InvalidDateException("Form date is bigger than To date");
        }


        Employee employee = employeeDao.getById(leaveApplication.getEmployeeId());
         if(employee == null)
        {
            throw new EmployeeNotFoundException("Employee with Id "+employee.getId()+" does not exist");
        }

        if (employee.getId().equals(leaveApplication.getEmployeeId())) {
            leaveApplication.setStatus(Status.APPLIED);
            if (leaveApplication.getFromDate().getTime() - date.getTime() >= 1296000000)
            {
                leaveApplication.setType(Type.PLANNED_LEAVE);
                return leaveApplicationDao.request(leaveApplication);
            }
            else
            {
                leaveApplication.setType(Type.UNPLANNED_LEAVE);
                return leaveApplicationDao.request(leaveApplication);
            }
        }
        else
            throw new InvalidIdException("Id doesn't match!!!!");
    }

    @Override
    public boolean cancelRequest(String id) {

        leaveApplicationDao.getById(id);
       return leaveApplicationDao.cancelRequest(id);
    }

    @Override
    public boolean updateRequest(LeaveApplication leaveApplication, String id) {
        leaveApplicationDao.getById(id);
        return leaveApplicationDao.updateRequest(leaveApplication,id);

    }

    @Override
    public LeaveApplication getById(String id)
    {
       return leaveApplicationDao.getById(id);

    }

    @Override
    public List<LeaveApplication> checkRequest()
    {
        List<LeaveApplication> leaveApplications;

        List<LeaveApplication> leaveApplications1 = new ArrayList<>();

        /*if (indexName.isEmpty())//TODO: DB shouldn't be empty
            throw new RuntimeException("Index is empty!!!");
        else
        */    leaveApplications = leaveApplicationDao.getAll();

        for (LeaveApplication leaveApplication:leaveApplications)
        {
            if (leaveApplication.getStatus() == Status.APPLIED)
                leaveApplications1.add(leaveApplication);

        }
        return leaveApplications1;
    }

    @Override
    public boolean approveRequest(String id)// TODO: assign requesthandlers at post time only & if status if applied then only approve or reject
    {
        LeaveApplication leaveApplication = leaveApplicationDao.getById(id);
/*

        if (leaveApplication == null)
            throw new RuntimeException("Update can't be performed!!!");

        if (id.trim().length()<=0)
            throw new RuntimeException("Id is null!!");
        else if (leaveApplication.getId().trim().length() != id.trim().length())
            throw new RuntimeException("Id doesn't match");
*/
        if (leaveApplication.getId().equals(id)) {
           if (leaveApplication!= null) {
                 /*leaveApplication.setStatus(Status.APPROVED);
                 */
                 return leaveApplicationDao.approveRequest(leaveApplication, id);
            }
            else
                 throw new InvalidIdException("\"LeaveApplication with Id \"+id+\" doesn't exists!!!\"");

        }
        else
            throw new InvalidIdException("Id doesn't match!!!");
/*        LeaveApplication leaveApplication1 = leaveApplication;
            leaveApplicationDao.updateRequest(leaveApplication1,id);
    */    /*return leaveApplicationDao.approveRequest(id);*/
    }

    @Override
    public boolean declineRequest(String id)//todo: same as approve
    {
        LeaveApplication leaveApplication = leaveApplicationDao.getById(id);

        if (leaveApplication.getId().equals(id)) {
            if (leaveApplication!= null) {
                leaveApplication.setStatus(Status.REJECTED);
                return leaveApplicationDao.declineRequest(leaveApplication, id);
            }
            else
                throw new InvalidIdException("\"LeaveApplication with Id \"+id+\" doesn't exists!!!\"");

        }
        else
            throw new InvalidIdException("Id doesn't match!!!");
    }

    @Override
    public List<LeaveApplication> getAll()
    {
        if (indexName.isEmpty())//TODO: DB shouldn't be empty
            throw new IndexNotFoundException("Index is empty!!!");
        else
            return leaveApplicationDao.getAll();
    }

    @Override
    public List<LeaveApplication> getByDate(Date fromDate, Date toDate) {

        List<LeaveApplication> leaveApplications1;
        List<LeaveApplication> leaveApplications2 = new ArrayList<>();
/*
        LeaveApplicationService leaveApplicationService = null;
        */
     /*   if (fromDate == null || toDate == null )
            throw new InvalidDateException("Date is null!!!");
        else
     */     /*  leaveApplications1= leaveApplicationService.getAll();
*/
                leaveApplications1=leaveApplicationDao.getAll();
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
         /*   leaveApplications1 = leaveApplicationDao.getAll();

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
            }*/

    }
}
