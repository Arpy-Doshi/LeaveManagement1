package com.brevitaz.service;

import com.brevitaz.model.LeavePolicy;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksAction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LeavePolicyService
{
    public boolean create(LeavePolicy leavePolicy);
    public boolean update(LeavePolicy leavePolicy,String id);
    public boolean delete( String id);
    public LeavePolicy getById(String id);
    public List<LeavePolicy> getAll();


}
