package com.brevitaz.service.impl;

import com.brevitaz.dao.LeavePolicyDao;
import com.brevitaz.model.LeavePolicy;
import com.brevitaz.service.LeavePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeavePolicyServiceImpl implements LeavePolicyService {

    @Autowired
    private LeavePolicyDao leavePolicyDao;

    public boolean create(LeavePolicy leavePolicy){
        return leavePolicyDao.create(leavePolicy);
    }

    public boolean update(LeavePolicy leavePolicy,String id){
        return leavePolicyDao.update(leavePolicy,id);
    }
    public boolean delete(String id) {
        return leavePolicyDao.delete(id);
    }

    public LeavePolicy getById(String id) {
        return leavePolicyDao.getById(id);
    }

    public List<LeavePolicy> getAll()  {
        return leavePolicyDao.getAll();
    }

}
