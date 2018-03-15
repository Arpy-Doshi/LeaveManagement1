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
    public boolean create(LeavePolicy leavePolicy) {
            return leavePolicyDao.create(leavePolicy);
    }

    @Override
    public boolean update(LeavePolicy leavePolicy, String id)
    {
      return leavePolicyDao.update(leavePolicy,id);

    }

    @Override
    public boolean delete(String id)
    {
       return leavePolicyDao.delete(id);
    }

    @Override
    public LeavePolicy getById(String id) {
        return leavePolicyDao.getById(id);
    }

    @Override
    public List<LeavePolicy> getAll() {
        return leavePolicyDao.getAll();
    }
}
