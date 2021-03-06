package com.brevitaz.service;

import com.brevitaz.model.LeaveApplication;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public interface LeaveApplicationService
{
    public boolean request(LeaveApplication leaveApplication);

    public boolean cancelRequest(String id);

    public boolean updateRequest(LeaveApplication leaveApplication, String id) ;

    public LeaveApplication getById(String id);

    public List<LeaveApplication> checkRequest() ;

    public boolean approveRequest(String id);

    public boolean declineRequest( String id);

    public boolean statusUpdate(LeaveApplication leaveApplication,String id);

    public boolean finalStatusUpdate(String id);

   /* public LeaveApplication getReport();
*/

    public List<LeaveApplication> getAll();
    public List<LeaveApplication> getByDate(Date fromDate, Date toDate);


    public boolean checkProbation(String employeeId);

    public double checkBalance(String employeeId);
}
