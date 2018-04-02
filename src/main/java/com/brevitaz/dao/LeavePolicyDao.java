package com.brevitaz.dao;

import com.brevitaz.model.LeavePolicy;
import java.util.List;

public interface LeavePolicyDao
{
    public boolean create(LeavePolicy leavePolicy);
    public boolean update(LeavePolicy leavePolicy,String id);
    //public boolean delete( String id);
    public LeavePolicy getById(String id);
    public List<LeavePolicy> getAll();

}
