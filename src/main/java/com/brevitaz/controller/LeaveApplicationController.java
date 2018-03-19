package com.brevitaz.controller;

import com.brevitaz.dao.LeaveApplicationDao;
import com.brevitaz.model.LeaveApplication;
import com.brevitaz.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/leave-applications")
public class LeaveApplicationController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @RequestMapping(method = RequestMethod.POST)
    public boolean request(@Valid @RequestBody LeaveApplication leaveApplication)  {
        return leaveApplicationService.request(leaveApplication);
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    public boolean cancelRequest(@PathVariable String id)  {
        return leaveApplicationService.cancelRequest(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public boolean updateRequest(@RequestBody LeaveApplication leaveApplication,@PathVariable String id) {
        return leaveApplicationService.updateRequest(leaveApplication,id);
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.GET)
    public LeaveApplication getById(@PathVariable String id)  {
        return leaveApplicationService.getById(id);
    }

    @RequestMapping(value = "/check-requests" , method = RequestMethod.GET)// TODO : Should be part of getAll with filter
    public List<LeaveApplication> checkRequest()  {
        return leaveApplicationService.checkRequest();
    }

    @RequestMapping(value = "/{id}/approve" , method = RequestMethod.PUT)//status changes in service & request should be POST or PUT?
    public boolean approveRequest(@PathVariable String id) {
        return leaveApplicationService.approveRequest(id);
    }

    @RequestMapping(value = "/{id}/decline" , method = RequestMethod.PUT)//status changes in service
    public boolean declineRequest(@PathVariable String id) {
        return leaveApplicationService.declineRequest(id);
    }

    @RequestMapping(value = "/get-by-date/{fromDate}/{toDate}" , method = RequestMethod.GET)
    public List<LeaveApplication> getByDate(@PathVariable("fromDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate, @PathVariable("toDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate) {
        return leaveApplicationService.getByDate(fromDate,toDate);// TODO: done by applying filter
    }

    @RequestMapping(value = "" , method = RequestMethod.GET)
    public List<LeaveApplication> getAll()  {
        return leaveApplicationService.getAll();
    }

    //**************** what should i pass in below two methods ?
   /*
   @RequestMapping(value = "/get-report" , method = RequestMethod.GET)//2 things in service 1 is emp wise i.e. getById & 2 is date wise i.e.From Date To Date
    public LeaveApplication getReport()
    {
        //return leaveApplicationDao.getReport();
    }

*/
}
