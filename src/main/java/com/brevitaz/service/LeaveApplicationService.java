package com.brevitaz.service;


import com.brevitaz.model.LeaveApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

public interface LeaveApplicationService {

     public List<LeaveApplication> getByDate(Date fromDate,Date toDate);
     public List<LeaveApplication> getAll();


}