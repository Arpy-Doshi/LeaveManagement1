package com.brevitaz.service.impl;

import com.brevitaz.dao.EmployeeDao;
import com.brevitaz.dao.LeaveApplicationDao;
import com.brevitaz.errors.InvalidDateException;
import com.brevitaz.errors.EmployeeNotFoundException;
import com.brevitaz.errors.InvalidIdException;
import com.brevitaz.errors.LeaveApplicationNotFoundException;
import com.brevitaz.model.Employee;
import com.brevitaz.model.LeaveApplication;
import com.brevitaz.model.Status;
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

       /* if(leaveApplication.getToDate().equals("") *//**//*||leaveApplication.getFromDate().equals("") || leaveApplication.getType().equals("")*//**//*)
        {
            System.out.println("Todate s");
        }
*/
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
            throw new EmployeeNotFoundException("employee does not exist");
        }



       /* if(leaveApplication.getEmployeeId().trim().length() >0 && employee.getName().trim().length()>0)
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
        }*/
         return false;
    }

    @Override
    public boolean cancelRequest(String id) {
        LeaveApplication leaveApplication = leaveApplicationDao.getById(id);
        if(leaveApplication == null)
        {
            throw new InvalidIdException("LeaveApplication with Id "+id+" does not exist");
        }
        return leaveApplicationDao.cancelRequest(id);
    }

    @Override
    public boolean updateRequest(LeaveApplication leaveApplication, String id) {
        LeaveApplication leaveApplication1 = leaveApplicationDao.getById(id);
        if(leaveApplication1 == null) {
            throw new InvalidIdException("LeaveApplication with Id " + id + " does not exist");
        }
        return leaveApplicationDao.updateRequest(leaveApplication,id);
    }

    @Override
    public LeaveApplication getById(String id)
    {/*
        boolean exists = client..admin().indices()
                .prepareExists(indexName)
                .execute().actionGet().isExists();*/

        if (id.trim().length()<=0)
            throw new RuntimeException("Id is null!!");
        else /*if (client.exists())*///TODO: DB shouldn't be null
            return leaveApplicationDao.getById(id);
        /*else
            throw new RuntimeException("Bad Request!!!!");*/

    }

    @Override
    public List<LeaveApplication> checkRequest()
    {
        List<LeaveApplication> leaveApplications;

        List<LeaveApplication> leaveApplications1 = null;

        if (indexName.isEmpty())//TODO: DB shouldn't be empty
            throw new RuntimeException("Index is empty!!!");
        else
            leaveApplications = leaveApplicationDao.getAll();

        for (LeaveApplication leaveApplication:leaveApplications)
        {
            if (leaveApplication.getStatus() == Status.APPLIED)
                leaveApplications1.add(leaveApplication);

        }
        return leaveApplications1;

        /*return leaveApplicationDao.checkRequest();*/
    }

    @Override
    public boolean approveRequest(String id)
    {
        LeaveApplication leaveApplication = leaveApplicationDao.getById(id);

        if (id.trim().length()<=0)
            throw new RuntimeException("Id is null!!");
        else if (leaveApplication.getId().trim().length() != id.trim().length())
            throw new RuntimeException("Id doesn't match");
        else if (leaveApplication!= null)
            leaveApplication.setStatus(Status.APPROVED);
            return leaveApplicationDao.approveRequest(id);
        /*        LeaveApplication leaveApplication1 = leaveApplication;
            leaveApplicationDao.updateRequest(leaveApplication1,id);
    */    /*return leaveApplicationDao.approveRequest(id);*/
    }

    @Override
    public boolean declineRequest(String id)
    {
        LeaveApplication leaveApplication = leaveApplicationDao.getById(id);

        if (id.trim().length()<=0)
            throw new RuntimeException("ID is null!!!");

        else if (leaveApplication.getId().trim().length()<= 0 || leaveApplication.getEmployeeId().trim().length()<=0)
            throw new RuntimeException("Field is null");

        else if (leaveApplication.getId().trim().length() != id.trim().length())
            throw new RuntimeException("Id doesn't match");

        else if (leaveApplication!= null && leaveApplication.getId().equals(id))// TODO: compare with Db if not empty then only
            return leaveApplicationDao.declineRequest(id);

        else
            throw new RuntimeException("Bad Request!!");



        /*return leaveApplicationDao.declineRequest(id);*/
    }

    @Override
    public List<LeaveApplication> getAll()
    {
        if (indexName.isEmpty())//TODO: DB shouldn't be empty
            throw new RuntimeException("Index is empty!!!");
        else
            return leaveApplicationDao.getAll();
    }

    @Override
    public List<LeaveApplication> getByDate(Date fromDate, Date toDate) {

        List<LeaveApplication> leaveApplications1;
        List<LeaveApplication> leaveApplications2 = new ArrayList<>();

        if (fromDate == null || toDate == null )
            throw new RuntimeException("Date is null!!!");
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
