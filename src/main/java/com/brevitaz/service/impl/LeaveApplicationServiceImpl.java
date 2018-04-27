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
import org.elasticsearch.discovery.zen.MembershipAction;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService
{
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LeaveApplicationServiceImpl.class);


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

    @Value("${Planned-Leave-To-Be-Applied-Before-Days}")
    private long leaveToBeAppliedBefore;

    @Value("${Probation-Period-Months}")
    private long probationPeriod;

    @Value("${Limit-For-Cancelling-Leave}")
    private long cancellationLimit;


    @Override
    public boolean request(LeaveApplication leaveApplication)
    {

         Employee employee = employeeDao.getById(leaveApplication.getEmployeeId());

         if (employee.getName().equals(leaveApplication.getEmployeeName()) && employee.getId().equals(leaveApplication.getEmployeeId())) {

             boolean isProbation =leaveApplicationService.checkProbation(leaveApplication.getEmployeeId());
            // double remainingBalance = leaveApplicationService.checkBalance(leaveApplication.getEmployeeId());

             if (isProbation == true) {
                 LOGGER.error("Error while requesting leave, Employee with id " + leaveApplication.getEmployeeId() + " is not allowed to make request as probabtion period is going on");
                 throw new NotAllowedException("Employee with id " + leaveApplication.getEmployeeId() + " is Not Allowed to make Request as probation period is going on");
             }
             else {
                 DateTime fromDate = new DateTime(leaveApplication.getFromDate());
                 DateTime toDate = new DateTime(leaveApplication.getToDate());
                 DateTime currentDate = new DateTime();

                 if (fromDate.isBefore(currentDate)) {
                     LOGGER.error("Error while requesting leave, fromDate is invalid");
                     throw new InvalidDateException("fromDate is invalid");
                 }
                 if (toDate.isBefore(currentDate)) {
                     LOGGER.error("Error while requesting leave, toDate is invalid");
                     throw new InvalidDateException("toDate is invalid");
                 }
                 if (fromDate.isAfter(toDate)) {
                     LOGGER.error("Error while requesting leave, fromDate is bigger than toDate which is invalid");
                     throw new InvalidDateException("fromDate is bigger than toDate which is invalid");
                 }

                 leaveApplication.setStatus(Status.APPLIED);

                 long days = Days.daysBetween(currentDate, fromDate).getDays();
                 System.out.println(days);
                 if (days >= leaveToBeAppliedBefore) {
                     leaveApplication.setType(Type. PLANNED_LEAVE);
                     return leaveApplicationDao.request(leaveApplication);
                 } else {
                     leaveApplication.setType(Type.UNPLANNED_LEAVE);
                     return leaveApplicationDao.request(leaveApplication);
                 }
             }

         }
         else
         {
             LOGGER.error("Error while requesting leave, employee with id "+leaveApplication.getEmployeeId()+" does not exist");
             throw new InvalidIdException("Employee doesn't match!!!");
         }
    }

    @Override
    public boolean checkProbation(String employeeId)
    {
        Employee employee = employeeDao.getById(employeeId);

        DateTime dateOfJoining = new DateTime(employee.getDateOfJoining());
        DateTime currentDate = new DateTime();

        int months =Months.monthsBetween(currentDate, dateOfJoining).getMonths();

       if (months > probationPeriod)
           return true;
       else
           return false;
    }

    @Override
    public double checkBalance(String employeeId)
    {
        List<LeaveApplication> leaveApplications = leaveApplicationDao.getByEmployeeId(employeeId);
        List<LeaveApplication> leaveApplications1 = new ArrayList<>();

        DateTime currentDate = new DateTime();

        double balance=4, deductionBalance =0;
        double time = 0,days =0;

        for (LeaveApplication leaveApplication : leaveApplications) {
            if (leaveApplication.getStatus() == Status.APPLIED
                    || leaveApplication.getStatus() == Status.APPROVED) {
                leaveApplications1.add(leaveApplication);
            }
        }

        for (LeaveApplication leaveApplication1 : leaveApplications1)
        {
            DateTime eachFromDate = new DateTime(leaveApplication1.getFromDate());
            DateTime eachToDate = new DateTime(leaveApplication1.getToDate());

            int x= currentDate.getMonthOfYear();
            if (x<4) {
                if (eachFromDate.getMonthOfYear() < 4) {
                    if (eachFromDate == eachToDate) {
                        if (eachFromDate.getHourOfDay() == 19 || eachToDate.getHourOfDay() == 19) {
                            time = 0.5;
                        }
                    }
                    if (eachToDate.isAfter(eachFromDate)) {
                        if (eachFromDate.getHourOfDay() == 19 && eachToDate.getHourOfDay() == 19) {
                            days = days + 1;
                        } else if (eachFromDate.getHourOfDay() == 19 || eachToDate.getHourOfDay() == 19) {
                            time = 0.5;
                            days = days + (eachToDate.getDayOfYear() - eachFromDate.getDayOfYear());
                        } else {
                            days = days + (eachToDate.getDayOfYear() - eachFromDate.getDayOfYear());
                        }
                    }
                    deductionBalance = deductionBalance + days + time;

                }
            }
            else if (x>3 && x<7 )
            {
                if (eachFromDate.getMonthOfYear()>3 && eachFromDate.getMonthOfYear() < 7) {
                    if (eachFromDate == eachToDate) {
                        if (eachFromDate.getHourOfDay() == 19 || eachToDate.getHourOfDay() == 19) {
                            time = 0.5;
                        }
                    }
                    if (eachToDate.isAfter(eachFromDate)) {
                        if (eachFromDate.getHourOfDay() == 19 && eachToDate.getHourOfDay() == 19) {
                            days = days + 1;
                        } else if (eachFromDate.getHourOfDay() == 19 || eachToDate.getHourOfDay() == 19) {
                            time = 0.5;
                            days = days + (eachToDate.getDayOfYear() - eachFromDate.getDayOfYear());
                        } else {
                            days = days + (eachToDate.getDayOfYear() - eachFromDate.getDayOfYear());
                        }
                    }
                    deductionBalance = deductionBalance + days + time;

                }
            }
            else if (x>6 && x<10)
            {
                if (eachFromDate.getMonthOfYear()>6 && eachFromDate.getMonthOfYear() < 10) {
                    if (eachFromDate == eachToDate) {
                        if (eachFromDate.getHourOfDay() == 19 || eachToDate.getHourOfDay() == 19) {
                            time = 0.5;
                        }
                    }
                    if (eachToDate.isAfter(eachFromDate)) {
                        if (eachFromDate.getHourOfDay() == 19 && eachToDate.getHourOfDay() == 19) {
                            days = days + 1;
                        } else if (eachFromDate.getHourOfDay() == 19 || eachToDate.getHourOfDay() == 19) {
                            time = 0.5;
                            days = days + (eachToDate.getDayOfYear() - eachFromDate.getDayOfYear());
                        } else {
                            days = days + (eachToDate.getDayOfYear() - eachFromDate.getDayOfYear());
                        }
                    }
                    deductionBalance = deductionBalance + days + time;

                }
            }
            else {
                if (eachFromDate.getMonthOfYear()>9 && eachFromDate.getMonthOfYear() < 13) {
                    if (eachFromDate == eachToDate) {
                        if (eachFromDate.getHourOfDay() == 19 || eachToDate.getHourOfDay() == 19) {
                            time = 0.5;
                        }
                    }
                    if (eachToDate.isAfter(eachFromDate)) {
                        if (eachFromDate.getHourOfDay() == 19 && eachToDate.getHourOfDay() == 19) {
                            days = days + 1;
                        } else if (eachFromDate.getHourOfDay() == 19 || eachToDate.getHourOfDay() == 19) {
                            time = 0.5;
                            days = days + (eachToDate.getDayOfYear() - eachFromDate.getDayOfYear());
                        } else {
                            days = days + (eachToDate.getDayOfYear() - eachFromDate.getDayOfYear());
                        }
                    }
                    deductionBalance = deductionBalance + days + time;

                }
            }

            balance = balance - deductionBalance;
        }
            return balance;
    }

    @Override
    public boolean cancelRequest(String id) {

        LeaveApplication leaveApplication = leaveApplicationDao.getById(id);
        if(leaveApplication == null)
        {
            LOGGER.error("Error while cancelling request : Leave Application with id {} does not exist",id);
            throw new InvalidIdException("id is invalid");
        }
        else
        {
            DateTime currentDate = new DateTime();
            DateTime fromDate = new DateTime(leaveApplication.getFromDate());

            long days = Days.daysBetween(fromDate,currentDate).getDays();
            if(days <= cancellationLimit)
            {
                leaveApplication.setStatus(Status.CANCELLED);
                return leaveApplicationDao.updateRequest(leaveApplication, id);
            }
            else
            {
                LOGGER.error("Error while cancelling Leave Application having id {} after 7 days",id);
                throw new NotAllowedException("can not allow to cancel after 7 days");
            }
        }
    }

    @Override
    public boolean updateRequest(LeaveApplication leaveApplication, String id) {
       LeaveApplication leaveApplication1 = leaveApplicationDao.getById(id);
       if(leaveApplication1 == null)
       {
           LOGGER.error("Error while updating Leave Application : Leave Application having id {} does not exist",id);
           throw new InvalidIdException("id is invalid");
       }
       else {

           DateTime fromDate = new DateTime(leaveApplication.getFromDate());
           DateTime toDate = new DateTime(leaveApplication.getToDate());
           DateTime currentDate = new DateTime();

           if (fromDate.isBefore(currentDate)) {
               LOGGER.error("Error while updating LeaveRequeest, fromDate is invalid");
               throw new InvalidDateException("fromDate is invalid");
           }
           if (toDate.isBefore(currentDate)) {
               LOGGER.error("Error while updating LeaveRequeest, toDate is invalid");
               throw new InvalidDateException("toDate is invalid");
           }
           if (fromDate.isAfter(toDate)) {
               LOGGER.error("Error while updating LeaveRequeest, fromDate is bigger than toDate which is invalid");
               throw new InvalidDateException("fromDate is bigger than toDate which is invalid");
           }
           leaveApplication.setStatus(Status.APPLIED);
           return leaveApplicationDao.updateRequest(leaveApplication, id);
       }
    }

    @Override
    public LeaveApplication getById(String id) {

        LeaveApplication leaveApplication = leaveApplicationDao.getById(id);
        if(leaveApplication == null)
        {
            LOGGER.error("Error while executing getById method : Leave Application having id {} does not exist",id);
            throw new InvalidIdException("id is invalid");
        }
        else return leaveApplication;
    }

    @Override
    public List<LeaveApplication> getAll()
    {
        return leaveApplicationDao.getAll();
    }


    @Override
    public List<LeaveApplication> checkRequest()
    {
        List<LeaveApplication> leaveApplications = leaveApplicationDao.getAll()
                .parallelStream()
                .filter(leaveApplication -> leaveApplication.getStatus() == Status.APPLIED)
                .collect(Collectors.toList());

        if(leaveApplications == null)
        {
            LOGGER.info("No Leave Application to be checked");
        }
            return leaveApplications;
    }

    @Override   //TODO : what should be the time limit for approval and rejection?
    public boolean statusUpdate(LeaveApplication leaveApplication, String id) {

        LeaveApplication leaveApplication1 = leaveApplicationDao.getById(id);
        if(leaveApplication1 == null)
        {
            LOGGER.error("Error while updating status : Leave Application having id {} deos not exist",id);
            throw new InvalidIdException("id is invalid");
        }
        else {
            boolean b = leaveApplicationDao.updateRequest(leaveApplication, id);
            leaveApplicationService.finalStatusUpdate(id);

            return b;
        }
    }

    @Override
    public boolean finalStatusUpdate(String id) {
        LeaveApplication leaveApplication = leaveApplicationDao.getById(id);

        try{
            if(leaveApplication.getStatus() != Status.CANCELLED) {

                if(leaveApplication.getHr().getStatus() == Status.APPROVED && leaveApplication.getAdmin().getStatus() == Status.APPROVED)
                {
                    leaveApplication.setStatus(Status.APPROVED);
                    return leaveApplicationDao.updateRequest(leaveApplication, id);
                }
                else
                {
                    leaveApplication.setStatus(Status.REJECTED);
                    return leaveApplicationDao.updateRequest(leaveApplication, id);
                }
            }
            else
            {
                LOGGER.error("Error while updating status, LeaveApplication with id "+id+" is already cancelled");
                throw new NotAllowedException("LeaveAppliaction with id "+id+" is already cancelled");
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
        DateTime currentDate = new DateTime();

        /*if (fromDateJoda.isAfter(currentDate)) {
            throw new InvalidDateException("fromDate is invalid");
        }
        if (toDateJoda.isAfter(currentDate)) {
            throw new InvalidDateException("toDate is invalid");
        }
         if(fromDateJoda.isAfter(toDateJoda))
        {
            throw new InvalidDateException("fromDate is invalid as fromDate can not be bigger than toDate");
        }
*/
        /*List<LeaveApplication> leaveApplications = new ArrayList<>();

        List<LeaveApplication> leaveApplications1 = leaveApplicationDao.getAll();

        for(LeaveApplication leaveApplication: leaveApplications1)
        {
            DateTime fromDateLeaveJoda = new DateTime(leaveApplication.getFromDate());

            if(fromDateLeaveJoda.isAfter(fromDateJoda.minusDays(1)) && fromDateLeaveJoda.isBefore(toDateJoda.plusDays(1)))
            {
                leaveApplications.add(leaveApplication);
            }
        }
        if(leaveApplications.isEmpty())
        {
            throw new NoContentException("No content found");
        }
        else
        {
            return leaveApplications;
        }*/
       List<LeaveApplication> leaveApplications = leaveApplicationDao.getByDate(fromDate,toDate);
       return leaveApplications;

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
