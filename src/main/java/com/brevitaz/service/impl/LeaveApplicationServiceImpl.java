package com.brevitaz.service.impl;

import com.brevitaz.dao.LeaveApplicationDao;
import com.brevitaz.model.LeaveApplication;
import com.brevitaz.service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService
{

    @Autowired
    LeaveApplicationDao leaveApplicationDao;

    @Override
    public List<LeaveApplication> getByDate(Date fromDate, Date toDate) {

       List<LeaveApplication> application = getAll();
        List<LeaveApplication> application2 = new ArrayList<>();
       for (LeaveApplication application1 : application )
       {
           if(fromDate.compareTo(application1.getFromDate()) == 0)
           {
               application2.add(application1);
           }
           else if(toDate.compareTo(application1.getToDate()) == 0)
           {
               application2.add(application1);
           }
           else if(fromDate.compareTo(application1.getFromDate()) == 1 && fromDate.compareTo(application1.getToDate()) == -1 )
           {
               application2.add(application1);
           }
       }
        return application2;
    }

    @Override
    public List<LeaveApplication> getAll() {
        System.out.println("getAll service call");
       return leaveApplicationDao.getAll();
    }
}
