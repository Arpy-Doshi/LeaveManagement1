package com.brevitaz.service.impl;

import com.brevitaz.dao.EmployeeDao;
import com.brevitaz.dao.LeaveApplicationDao;
import com.brevitaz.errors.*;
import com.brevitaz.model.Employee;
import com.brevitaz.model.LeaveApplication;
import com.brevitaz.model.LeavePolicy;
import com.brevitaz.model.Status;
import com.brevitaz.model.Type;
import com.brevitaz.service.LeaveApplicationService;
import org.elasticsearch.client.RestHighLevelClient;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService
{
    @Autowired
    private LeaveApplicationDao leaveApplicationDao;

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private RestHighLevelClient client;

    @Value("${LeaveApplication-Index-Name}")
    private String indexName;


    @Override
    public boolean request(LeaveApplication leaveApplication)// TODO: JodaTime for time condition.
     {// Todo: Add method for checking probation condition, check balance. Create structure as per acceptance criteria.

         Employee employee = employeeDao.getById(leaveApplication.getEmployeeId());

         if (employee.getName().equals(leaveApplication.getEmployeeName()) && employee.getId().equals(leaveApplication.getEmployeeId())) {

             boolean isProbation =leaveApplicationService.checkProbation(leaveApplication.getEmployeeId());

             if (isProbation == true)
                 throw new NotAllowedException("Employee with id "+leaveApplication.getEmployeeId()+" is Not Allowed  to make Request!!!");
             else {

                 DateTime fromDate = new DateTime(leaveApplication.getFromDate());
                 DateTime toDate = new DateTime(leaveApplication.getToDate());
                 DateTime currentDate = new DateTime();

                 if (fromDate.isBefore(currentDate)) {
                     throw new InvalidDateException("fromDate is invalid");
                 }
                 if (toDate.isBefore(currentDate)) {
                     throw new InvalidDateException("toDate is invalid");
                 }
                 if (fromDate.isAfter(toDate)) {
                     throw new InvalidDateException("fromDate is bigger than toDate which is invalid");
                 }

                 leaveApplication.setStatus(Status.APPLIED);


                 long days = Days.daysBetween(currentDate, fromDate).getDays();
                 System.out.println(days);
                 if (days >= 15) {
                     leaveApplication.setType(Type.PLANNED_LEAVE);
                     return leaveApplicationDao.request(leaveApplication);
                 } else {
                     leaveApplication.setType(Type.UNPLANNED_LEAVE);
                     return leaveApplicationDao.request(leaveApplication);
                 }
             }

         }
         else
         {
             throw new InvalidIdException("Employee doesn't match!!!");
         }
      /*  Date date = new Date();
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
*/





       /*  leaveApplication.setStatus(Status.APPLIED);
            if (leaveApplication.getFromDate().getTime() - date.getTime() >= 1296000000)
            {
                leaveApplication.setType(Type.PLANNED_LEAVE);
                return leaveApplicationDao.request(leaveApplication);
            }
            else
            {
                leaveApplication.setType(Type.UNPLANNED_LEAVE);
                return leaveApplicationDao.request(leaveApplication);
            }*/

    }

    @Override
    public boolean checkProbation(String employeeId)
    {
        Employee employee = employeeDao.getById(employeeId);

        DateTime dateOfJoining = new DateTime(employee.getDateOfJoining());
        DateTime currentDate = new DateTime();

        long days = Days.daysBetween(dateOfJoining, currentDate).getDays();
        System.out.println(days);
        if (days <= 10)
            return true;
        else
            return false;
    }



    @Override
    public boolean cancelRequest(String id) {
        LeaveApplication leaveApplication = leaveApplicationDao.getById(id);
        leaveApplication.setStatus(Status.CANCELLED);
       return leaveApplicationDao.updateRequest(leaveApplication,id);
    }

    @Override
    public boolean updateRequest(LeaveApplication leaveApplication, String id) {
        leaveApplicationDao.getById(id);

        DateTime fromDate = new DateTime(leaveApplication.getFromDate());
        DateTime toDate = new DateTime(leaveApplication.getToDate());
        DateTime currentDate = new DateTime();

        if (fromDate.isBefore(currentDate)) {
            throw new InvalidDateException("fromDate is invalid");
        }
        if (toDate.isBefore(currentDate)) {
            throw new InvalidDateException("toDate is invalid");
        }
        if (fromDate.isAfter(toDate)) {
            throw new InvalidDateException("fromDate is bigger than toDate which is invalid");
        }
        leaveApplication.setStatus(Status.APPLIED);
        return leaveApplicationDao.updateRequest(leaveApplication,id);
    }

    @Override
    public LeaveApplication getById(String id) {
        return leaveApplicationDao.getById(id);
    }

    @Override
    public List<LeaveApplication> getAll() {
        List<LeaveApplication> leaveApplications = leaveApplicationDao.getAll();
        if(leaveApplications == null)
        {
            throw new NoContentException("No content found");
        }
        else
        {
            return leaveApplications;
        }
    }


    @Override
    public List<LeaveApplication> checkRequest()
    {
        List<LeaveApplication> leaveApplications;

        List<LeaveApplication> leaveApplications1 = new ArrayList<>();

        /*if (indexName.isEmpty())
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
    public boolean statusUpdate(LeaveApplication leaveApplication, String id) {
        boolean b = leaveApplicationDao.updateRequest(leaveApplication,id);

        leaveApplicationService.finalStatusUpdate(id);

        return b;
    }

    @Override
    public boolean finalStatusUpdate(String id) {
        LeaveApplication leaveApplication = leaveApplicationDao.getById(id);

        try{
            if(leaveApplication.getHr().getStatus() == leaveApplication.getAdmin().getStatus())
            {
                if(leaveApplication.getHr().getStatus()==Status.REJECTED && leaveApplication.getAdmin().getStatus()==Status.REJECTED)
                {
                    leaveApplication.setStatus(Status.REJECTED);
                    return leaveApplicationDao.updateRequest(leaveApplication,id);
                }
                else
                {
                    leaveApplication.setStatus(Status.APPROVED);
                    return leaveApplicationDao.updateRequest(leaveApplication, id);
                }
            }
            else
            {
                leaveApplication.setStatus(Status.REJECTED);
                return leaveApplicationDao.updateRequest(leaveApplication,id);
            }
        }
        catch(NullPointerException e)
        {
            return false;
        }
    }

    @Override
    public List<LeaveApplication> getByDate(Date fromDate, Date toDate) {

        DateTime fromDateJoda = new DateTime(fromDate);
        DateTime toDateJoda = new DateTime(toDate);

        List<LeaveApplication> leaveApplications = new ArrayList<>();

        List<LeaveApplication> leaveApplications1 = leaveApplicationDao.getAll();

        for(LeaveApplication leaveApplication: leaveApplications1)
        {
            DateTime fromDateLeaveJoda = new DateTime(leaveApplication.getFromDate());
            System.out.println(toDateJoda);
            System.out.println(fromDateLeaveJoda);
            System.out.println(toDateJoda.plusDays(1));
            System.out.println(fromDateJoda.isEqual(fromDateLeaveJoda));

            if(fromDateJoda.isAfter(fromDateLeaveJoda.minusDays(1)))
            {
                System.out.println(fromDateJoda.isEqual(fromDateLeaveJoda));
                leaveApplications.add(leaveApplication);
            }
            else if(toDateJoda.isEqual(fromDateLeaveJoda.plusDays(1)))
            {
                leaveApplications.add(leaveApplication);
            }
            else if(fromDateLeaveJoda.isAfter(fromDateJoda) && fromDateLeaveJoda.isBefore(toDateJoda))
            {
                leaveApplications.add(leaveApplication);
            }
        }
        return leaveApplications;



    /*    List<LeaveApplication> leaveApplications1;
        List<LeaveApplication> leaveApplications2 = new ArrayList<>();

        LeaveApplicationService leaveApplicationService = null;

        if (fromDate == null || toDate == null )
            throw new InvalidDateException("Date is null!!!");
        else
            leaveApplications1= leaveApplicationService.getAll();

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
        return leaveApplications2;  */


    }

    @Override
    public boolean approveRequest(String id)
    {
        /*LeaveApplication leaveApplication = leaveApplicationDao.getById(id);
*//*

        if (leaveApplication == null)
            throw new RuntimeException("Update can't be performed!!!");

        if (id.trim().length()<=0)
            throw new RuntimeException("Id is null!!");
        else if (leaveApplication.getId().trim().length() != id.trim().length())
            throw new RuntimeException("Id doesn't match");
e*//*
        if (leaveApplication.getId().equals(id)) {
            if (leaveApplication!= null) {
                 *//*leaveApplication.setStatus(Status.APPROVED);
                 *//*
                return leaveApplicationDao.approveRequest(leaveApplication, id);
            }
            else
                throw new InvalidIdException("\"LeaveApplication with Id \"+id+\" doesn't exists!!!\"");
        }
        else
            throw new InvalidIdException("Id doesn't match!!!");*/
        return false;
    }

    @Override
    public boolean declineRequest(String id)//
    {
       /* LeaveApplication leaveApplication = leaveApplicationDao.getById(id);

        if (leaveApplication.getId().equals(id)) {
            *//*if (leaveApplication!= null) {
            *//**//*    leaveApplication.setStatus(Status.REJECTED);
              *//*  return leaveApplicationDao.declineRequest(leaveApplication, id);
            *//*}
            else
                throw new InvalidIdException("\"LeaveApplication with Id \"+id+\" doesn't exists!!!\"");
*//*
        }
        else
            throw new InvalidIdException("Id doesn't match!!!");*/
        return false;
    }
}
