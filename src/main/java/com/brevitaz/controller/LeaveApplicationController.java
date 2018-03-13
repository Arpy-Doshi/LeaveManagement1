package com.brevitaz.controller;

import com.brevitaz.dao.LeaveApplicationDao;
import com.brevitaz.model.LeaveApplication;
import com.brevitaz.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/leave-applications")
public class LeaveApplicationController {

    @Autowired
    private LeaveApplicationDao leaveApplicationDao;

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @RequestMapping(method = RequestMethod.POST)//do count type of leave in ser\221 1`vice
    public boolean request(@RequestBody LeaveApplication leaveApplication)  {
        return leaveApplicationDao.request(leaveApplication);
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    public boolean cancelRequest(@PathVariable String id)  {
        return leaveApplicationDao.cancelRequest(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public boolean updateRequest(@RequestBody LeaveApplication leaveApplication,@PathVariable String id) {
        return leaveApplicationDao.updateRequest(leaveApplication,id);
    }

    /*@RequestMapping(value = "/employee/{employeeId}/balance" , method = RequestMethod.GET)//remaining
    public double checkBalance(@PathVariable String employeeId)
    {
        return 0;
    }// TODO: it should be in leave controller

    @RequestMapping(value = "/employee/{employeeId}" , method = RequestMethod.GET) // personal record.
    public List<LeaveApplication> getByEmployeeId( @PathVariable String employeeId)  {
        return leaveApplicationDao.getByEmployeeId(employeeId);// TODO:should be in employee controller
    }*/

    @RequestMapping(value = "/{id}" , method = RequestMethod.GET) // for single leave.
    public LeaveApplication getById(@PathVariable String id)  {
        return leaveApplicationDao.getById(id);
    }

    @RequestMapping(value = "/check-requests" , method = RequestMethod.GET)// TODO : Should be part of getAll with filter
    public List<LeaveApplication> checkRequest()  {
        return leaveApplicationDao.checkRequest();
    }

    @RequestMapping(value = "/{id}/approve" , method = RequestMethod.PUT)//status changes in service & request should be POST or PUT?
    public boolean approveRequest(@PathVariable String id) {
        return leaveApplicationDao.approveRequest(id);
    }

    @RequestMapping(value = "/{id}/decline" , method = RequestMethod.PUT)//status changes in service
    public boolean declineRequest(@PathVariable String id) {
        return leaveApplicationDao.declineRequest(id);
    }

    // we will get this list from service using getAll method.
    @RequestMapping(value = "/get-by-date/{fromDate}/{toDate}" , method = RequestMethod.GET)
    public List<LeaveApplication> getByDate(@PathVariable("fromDate") @DateTimeFormat(pattern = "ddMMyyyy") Date fromDate, @PathVariable("toDate") @DateTimeFormat(pattern = "ddMMyyyy") Date toDate) {
        return leaveApplicationService.getByDate(fromDate,toDate);// TODO: done by applying filter
    }

    @RequestMapping(value = "" , method = RequestMethod.GET) // all record.
    public List<LeaveApplication> getAll()  {
        return leaveApplicationDao.getAll();
    }

    //**************** what should i pass in below two methods ?
   /*
   @RequestMapping(value = "/get-report" , method = RequestMethod.GET)//2 things in service 1 is emp wise i.e. getById & 2 is date wise i.e.From Date To Date
    public LeaveApplication getReport()
    {
        //return leaveApplicationDao.getReport();
    }

*/
    @RequestMapping(value = "/duplicate" , method = RequestMethod.GET) // all record.
    public List<LeaveApplication> getDuplicate()  {
        Date fromDate = new Date(2018-03-02);
        Date toDate = new Date(2018-03-12);
        return leaveApplicationService.getByDate(fromDate,toDate);
    }
}
