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

        DateTime currentDate = new DateTime("12-12");

        DateTime date = new DateTime();

        double balance = 0,deductionBalance = 0;

        LocalDate dateTime = new LocalDate("01-01");

        Employee employee = employeeDao.getById(employeeId);
        DateTime doj = new DateTime(employee.getDateOfJoining());


        if (doj.getYear() == date.getYear()) {
            if (doj.getMonthOfYear() != (1) || doj.getMonthOfYear() != (4)
                    || doj.getMonthOfYear() != (7) || doj.getMonthOfYear() != (10)) {
                if (doj.getMonthOfYear() == (2) || doj.getMonthOfYear() != (5)
                        || doj.getMonthOfYear() != (8) || doj.getMonthOfYear() != (11)) {
                    balance = 3;
                    for (int x = doj.getMonthOfYear();x<13;x=x+2){
                        if (dateTime.plusMonths(x).getMonthOfYear() <= currentDate.getMonthOfYear()) {
                            balance = balance + 4;

                            if (dateTime.plusMonths(x =x + 3).getMonthOfYear() > currentDate.getMonthOfYear()
                                    ) {

                                for (LeaveApplication leaveApplication : leaveApplications) {
                                    if (leaveApplication.getStatus() == Status.APPLIED
                                            || leaveApplication.getStatus() == Status.APPROVED) {
                                        leaveApplications1.add(leaveApplication);
                                    }
                                }

                                for (LeaveApplication leaveApplication1 : leaveApplications1) {
                                    DateTime fromDate = new DateTime(leaveApplication1.getFromDate());
                                    DateTime toDate = new DateTime(leaveApplication1.getToDate());

                                    float duration = toDate.getDayOfYear() - fromDate.getDayOfYear();
                                    deductionBalance = deductionBalance + duration;
                                }

                                balance = balance - deductionBalance;

                                System.out.println("balance on " + date.toLocalDate() + " is " + balance);
                            }
                        }
                    }

                } else
                    balance = 2;


            }
        }
        if (date.getYear() >doj.getYear() ) {

            if (dateTime.dayOfYear().getAsText().equals("1")) {
                balance = 4;
            }

            if (dateTime.plusMonths(3).getMonthOfYear() > currentDate.getMonthOfYear()) {

                for (LeaveApplication leaveApplication : leaveApplications) {
                    if (leaveApplication.getStatus() == Status.APPLIED
                            || leaveApplication.getStatus() == Status.APPROVED) {
                        leaveApplications1.add(leaveApplication);
                    }
                }

                for (LeaveApplication leaveApplication1 : leaveApplications1) {
                    DateTime fromDate = new DateTime(leaveApplication1.getFromDate());
                    DateTime toDate = new DateTime(leaveApplication1.getToDate());

                    float duration = toDate.getDayOfYear() - fromDate.getDayOfYear();
                    deductionBalance = deductionBalance + duration;
                }

                balance = balance - deductionBalance;

                System.out.println("balance on " + date.toLocalDate() + " is " + balance);
            }

            if (dateTime.plusMonths(3).getMonthOfYear() <= currentDate.getMonthOfYear()) {
                balance = balance + 4;
                if (dateTime.plusMonths(6).getMonthOfYear() > currentDate.getMonthOfYear()
                        ) {

                    for (LeaveApplication leaveApplication : leaveApplications) {
                        if (leaveApplication.getStatus() == Status.APPLIED
                                || leaveApplication.getStatus() == Status.APPROVED) {
                            leaveApplications1.add(leaveApplication);
                        }
                    }

                    for (LeaveApplication leaveApplication1 : leaveApplications1) {
                        DateTime fromDate = new DateTime(leaveApplication1.getFromDate());
                        DateTime toDate = new DateTime(leaveApplication1.getToDate());

                        float duration = toDate.getDayOfYear() - fromDate.getDayOfYear();
                        deductionBalance = deductionBalance + duration;
                    }

                    balance = balance - deductionBalance;

                    System.out.println("balance on " + date.toLocalDate() + " is " + balance);
                }
            }

            if (dateTime.plusMonths(6).getMonthOfYear() <= currentDate.getMonthOfYear()) {
                balance = balance + 4;
                if (dateTime.plusMonths(9).getMonthOfYear() > currentDate.getMonthOfYear()
                        ) {

                    for (LeaveApplication leaveApplication : leaveApplications) {
                        if (leaveApplication.getStatus() == Status.APPLIED
                                || leaveApplication.getStatus() == Status.APPROVED) {
                            leaveApplications1.add(leaveApplication);
                        }
                    }

                    for (LeaveApplication leaveApplication1 : leaveApplications1) {
                        DateTime fromDate = new DateTime(leaveApplication1.getFromDate());
                        DateTime toDate = new DateTime(leaveApplication1.getToDate());

                        float duration = toDate.getDayOfYear() - fromDate.getDayOfYear();
                        deductionBalance = deductionBalance + duration;
                    }

                    balance = balance - deductionBalance;
                    System.out.println("balance on " + date.toLocalDate() + " is " + balance);
                }

            }

            if (dateTime.plusMonths(9).getMonthOfYear() <= currentDate.getMonthOfYear()) {
                balance = balance + 4;
                for (LeaveApplication leaveApplication : leaveApplications) {
                    if (leaveApplication.getStatus() == Status.APPLIED
                            || leaveApplication.getStatus() == Status.APPROVED) {
                        leaveApplications1.add(leaveApplication);
                    }
                }

                for (LeaveApplication leaveApplication1 : leaveApplications1) {
                    DateTime fromDate = new DateTime(leaveApplication1.getFromDate());
                    DateTime toDate = new DateTime(leaveApplication1.getToDate());

                    float duration = toDate.getDayOfYear() - fromDate.getDayOfYear();
                    deductionBalance = deductionBalance + duration;
                }

                balance = balance - deductionBalance;
                System.out.println("balance on " + date.toLocalDate() + " is " + balance);
            }

        }

        return balance;
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
