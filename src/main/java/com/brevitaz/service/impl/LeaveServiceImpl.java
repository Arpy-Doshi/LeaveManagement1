package com.brevitaz.service.impl;


import com.brevitaz.dao.EmployeeDao;
import com.brevitaz.dao.LeaveApplicationDao;

import com.brevitaz.model.Employee;
import com.brevitaz.model.LeaveApplication;
import com.brevitaz.model.Status;
import com.brevitaz.service.LeaveService;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Service
public class LeaveServiceImpl implements LeaveService
{
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private LeaveApplicationDao leaveApplicationDao;

    @Override
    public double checkBalance(String employeeId) {
        List<LeaveApplication> leaveApplications = leaveApplicationDao.getByEmployeeId(employeeId);

        List<LeaveApplication> leaveApplications1 = new ArrayList<>();

        DateTime lastMonth = new DateTime("12-12");

        DateTime currentDate = new DateTime();

        double balance = 0, deductionBalance = 0;

        LocalDate firstMonth = new LocalDate("01-01");

        Employee employee = employeeDao.getById(employeeId);
        DateTime doj = new DateTime(employee.getDateOfJoining());
        System.out.println(doj);
        String joiningYear = doj.year().getAsText();
        String currentYear = currentDate.year().getAsText();
        System.out.println(currentDate);

        for (LeaveApplication leaveApplication : leaveApplications) {
            if (leaveApplication.getStatus() == Status.APPLIED
                    || leaveApplication.getStatus() == Status.APPROVED) {
                leaveApplications1.add(leaveApplication);
            }
        }

        for (LeaveApplication leaveApplication1 : leaveApplications1) {
            DateTime fromDate = new DateTime(leaveApplication1.getFromDate());
            DateTime toDate = new DateTime(leaveApplication1.getToDate());

            System.out.println("fromDate : "+fromDate.hourOfDay().getAsText());
            System.out.println("toDate : "+toDate.hourOfDay().getAsText());

            double time = 0,days =0;
            if (fromDate == toDate) {
                if (fromDate.getHourOfDay() == 19 || toDate.getHourOfDay() == 19) {
                    time = 0.5;
                }
            }
            if (toDate.isAfter(fromDate)) {
                if (fromDate.getHourOfDay() == 19 && toDate.getHourOfDay() == 19) {
                    days = days + 1;
                } else if (fromDate.getHourOfDay() == 19 || toDate.getHourOfDay() == 19)
                {
                    time = 0.5;
                    days = days + (toDate.getDayOfYear() - fromDate.getDayOfYear());
                }
                else
                {
                    days = days + (toDate.getDayOfYear() - fromDate.getDayOfYear());
                }
            }
            deductionBalance = deductionBalance + days + time;

            System.out.println("Time"+time);
            System.out.println("Days"+days);
        }
        System.out.println("Deduction Balance"+deductionBalance);

        if (joiningYear.equals(currentYear)){
        if (doj.getMonthOfYear() != (1) || doj.getMonthOfYear() != (4)
                    || doj.getMonthOfYear() != (7) || doj.getMonthOfYear() != (10))
            {
                int x = doj.getMonthOfYear();
                if (doj.getMonthOfYear() == (2) || doj.getMonthOfYear() == (5)
                        || doj.getMonthOfYear() == (8) || doj.getMonthOfYear() == (11)) {
                    balance = 3;
                    for (x=doj.getMonthOfYear();x<12;x=x++){
                        if (firstMonth.plusMonths(x=x+2).getMonthOfYear() <= lastMonth.getMonthOfYear()) {
                            balance = balance + 4;
                            x=x+2;
                        }
                    }
                        System.out.println("doj 2 nd month"+balance);

                } else {
                    balance = 2;
                    for (x=doj.getMonthOfYear();x<12;x=x++){
                        if (firstMonth.plusMonths(x = x + 1).getMonthOfYear() <= lastMonth.getMonthOfYear()) {
                            balance = balance + 4;
                            x=x+2;
                        }
                    }
                    System.out.println("doj 5 th month"+balance);
                }
            }
        }
        else /*if (doj.getYear() < currentDate.getYear())*/{
            if (firstMonth.dayOfYear().getAsText().equals("1")) {
                balance = 4;
            }
            for (int i = 0; i < 12; i++)
            {
                if (firstMonth.plusMonths(i=i+2).getMonthOfYear() <= lastMonth.getMonthOfYear()) {
                    balance = balance + 4;
                    i=i+2;
                }
            }
            System.out.println("doj next year"+balance);
        }

        balance = balance - deductionBalance;
        System.out.println("balance on " + currentDate.toLocalDate() + " is " + balance);
            return balance;
    }


    @Override
    public List<LeaveApplication> getByEmployeeId(String employeeId)
    {
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
