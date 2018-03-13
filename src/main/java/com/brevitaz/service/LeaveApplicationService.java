package com.brevitaz.service;

import com.brevitaz.model.LeaveApplication;

import java.util.Date;
import java.util.List;

public interface LeaveApplicationService
{
    public List<LeaveApplication> getByDate(Date fromDate, Date toDate);
}
