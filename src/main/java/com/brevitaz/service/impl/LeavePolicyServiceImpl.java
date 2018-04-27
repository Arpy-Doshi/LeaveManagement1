package com.brevitaz.service.impl;

import com.brevitaz.dao.LeavePolicyDao;
import com.brevitaz.errors.InvalidIdException;
import com.brevitaz.model.LPStatus;
import com.brevitaz.model.LeavePolicy;
import com.brevitaz.service.LeavePolicyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LeavePolicyServiceImpl implements LeavePolicyService
{
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LeaveApplicationServiceImpl.class);

    @Autowired
    private LeavePolicyDao leavePolicyDao;

    @Autowired
    private LeavePolicyService leavePolicyService;

    @Override
    public boolean create(LeavePolicy leavePolicy)
    {
        leavePolicy.setStatus(LPStatus.CREATED);
        return leavePolicyDao.create(leavePolicy);
    }

    @Override
    public boolean update(LeavePolicy leavePolicy, String id)
    {
        if (leavePolicy.getId().equals(id))
            return leavePolicyDao.update(leavePolicy, id);

        else
        {
            LOGGER.error("Error while updating Leave Policy, Leave Policy with id "+id +" does not exist");
            throw new InvalidIdException("id is invalid");
        }
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

    @Override
    public List<LeavePolicy> getAll()
    {
        return leavePolicyDao.getAll();
    }
}
