package com.brevitaz.service.impl;

import com.brevitaz.dao.LeavePolicyDao;
import com.brevitaz.model.LeavePolicy;
import com.brevitaz.service.LeavePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeavePolicyServiceImpl implements LeavePolicyService
{

    @Autowired
    private LeavePolicyDao leavePolicyDao;

    @Override
    public boolean create(LeavePolicy leavePolicy)
    {
        if (leavePolicy.getId().trim().length()<=0 || leavePolicy.getName().trim().length()<=0)
            throw new RuntimeException("Field is Null");
         else if (leavePolicy.getLeavePolicyRules().isEmpty())
             throw new RuntimeException("Rules are empty!!");
         else if (leavePolicy != null)
            return leavePolicyDao.create(leavePolicy);
        else
            throw new RuntimeException("Bad Request!!!");
    }

    @Override
    public boolean update(LeavePolicy leavePolicy, String id)
    {
        LeavePolicy leavePolicy1 = leavePolicyDao.getById(leavePolicy.getId());

        if (id.trim().length()<=0)
            throw new RuntimeException("Id is null!!");
        else if (leavePolicy.getId().trim().length()<=0 || leavePolicy.getName().trim().length()<=0)
            throw new RuntimeException("Field is null!!!");
        else if (leavePolicy1.getId() != id)
            throw new RuntimeException("Id doesn't match");
        else if(leavePolicy!=null && leavePolicy.getId().equals(id))
            return leavePolicyDao.update(leavePolicy,id);
       else
            throw new RuntimeException("Bad Request!!!!");


    }

    @Override
    public boolean delete(String id)
    {
        LeavePolicy leavePolicy = leavePolicyDao.getById(id);

        if (id.trim().length()<=0)
            throw new RuntimeException("Id is null!!");
        else if (leavePolicy.getId().trim().length()<= 0 || leavePolicy.getName().trim().length()<=0)
            throw new RuntimeException("Field is null");
        else if (leavePolicy.getId() != id)
            throw new RuntimeException("Id doesn't match");
        else if (leavePolicy!= null && leavePolicy.getId().equals(id))
            return leavePolicyDao.delete(id);
        else
            throw new RuntimeException("Bad Request!!");
    }

    @Override
    public LeavePolicy getById(String id)
    {
        if (id.trim().length()<=0)
            throw new RuntimeException("Id is null!!");
        else
            return leavePolicyDao.getById(id);
    }

    @Override
    public List<LeavePolicy> getAll() {
        return leavePolicyDao.getAll();
    }
}
