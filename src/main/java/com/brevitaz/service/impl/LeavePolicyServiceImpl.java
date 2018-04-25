package com.brevitaz.service.impl;

import com.brevitaz.dao.LeavePolicyDao;
import com.brevitaz.errors.InvalidIdException;
import com.brevitaz.model.LPStatus;
import com.brevitaz.model.LeavePolicy;
import com.brevitaz.service.LeavePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LeavePolicyServiceImpl implements LeavePolicyService
{

    @Autowired
    private LeavePolicyDao leavePolicyDao;

    @Autowired
    private LeavePolicyService leavePolicyService;

    @Override
    public boolean create(LeavePolicy leavePolicy)
    {
       /* if (leavePolicy.getId().trim().length()<=0 || leavePolicy.getName().trim().length()<=0)
            throw new RuntimeException("Field is Null");
         else if (leavePolicy.getLeavePolicyRules().isEmpty())
             throw new RuntimeException("Rules are empty!!");
         else*//*
       if (leavePolicy != null)*/
            return leavePolicyDao.create(leavePolicy);
        /*else
            throw new InvalidIdException("");*/
    }

    @Override
    public boolean update(LeavePolicy leavePolicy, String id)
    {
        if (leavePolicy.getId().equals(id))
            return leavePolicyDao.update(leavePolicy, id);

        else
            throw new InvalidIdException("Id doesn't match!!!");

    }

    @Override
    public boolean cancel(String id) {
        LeavePolicy leavePolicy = leavePolicyService.getLatestPolicy();
        leavePolicy.setStatus(LPStatus.CANCELLED);
            return leavePolicyDao.update(leavePolicy,id);

    }

    @Override
    public LeavePolicy getLatestPolicy()
    {
        LeavePolicy leavePolicy= leavePolicyDao.getLatestPolicy();
        return leavePolicy;
    }

    /*@Override
    public  Date getNearestDate(List<Date> dates, Date currentDate) {
        long minDiff = -1, currentTime = currentDate.getTime();
        Date minDate = null;
        for (Date date : dates) {
            long diff = Math.abs(currentTime - date.getTime());
            if ((minDiff == -1) || (diff < minDiff)) {
                minDiff = diff;
                minDate = date;
            }
        }
        return minDate;
    }*/

    @Override
    public List<LeavePolicy> getAll()
    {
        /*if ()*/// TODO: compare with Db if not empty then only
        return leavePolicyDao.getAll();
        /*else
            throw new RuntimeException("Bad Request!!!");*/
    }
}
