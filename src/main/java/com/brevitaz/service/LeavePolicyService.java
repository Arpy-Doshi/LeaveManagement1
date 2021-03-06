package com.brevitaz.service;

import com.brevitaz.model.LeavePolicy;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksAction;
import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface LeavePolicyService
{
    public boolean create(LeavePolicy leavePolicy);
    public boolean update(LeavePolicy leavePolicy,String id);
    public boolean cancel( String id);
    public LeavePolicy getLatestPolicy();
    //public Date getNearestDate(List<Date> dates, Date currentDate);
    public List<LeavePolicy> getAll();


}
