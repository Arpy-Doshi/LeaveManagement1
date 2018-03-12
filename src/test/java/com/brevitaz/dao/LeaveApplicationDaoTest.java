package com.brevitaz.dao;

import com.brevitaz.model.LeaveApplication;
import com.brevitaz.model.Status;
import com.brevitaz.model.Type;
import com.brevitaz.model.Employee;
import com.carrotsearch.randomizedtesting.RandomizedRunner;
import com.carrotsearch.randomizedtesting.annotations.ThreadLeakScope;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.io.IOException;
import java.util.List;

@RunWith(RandomizedRunner.class)
@ThreadLeakScope(ThreadLeakScope.Scope.NONE)
@SpringBootTest
public class LeaveApplicationDaoTest {

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();
    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();
    @Autowired
    private LeaveApplicationDao leaveApplicationDao;

    @Test
    public void requestTest()  {
        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId("11");
        leaveApplication.setEmployeeId("AA");
        leaveApplication.setReason("xyz");
        leaveApplicationDao.request(leaveApplication);

        LeaveApplication leaveApplication1 = leaveApplicationDao.getById("11");
        Assert.assertEquals(leaveApplication1.getEmployeeId(),leaveApplication.getEmployeeId());

        leaveApplicationDao.cancelRequest("11");
    }

    @Test
    public void cancelRequestTest()
    {   LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId("11");
        leaveApplication.setEmployeeId("AA");
        leaveApplication.setReason("xyz");
        leaveApplicationDao.request(leaveApplication);

        leaveApplicationDao.cancelRequest("11");

        LeaveApplication leaveApplication1 = leaveApplicationDao.getById("11");
        Assert.assertNull(leaveApplication1);

    }

     @Test
    public void updateRequestTest()
    {
        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId("11");
        leaveApplication.setEmployeeId("AA");
        leaveApplication.setReason("xyz");
        leaveApplication.setStatus(Status.APPLIED);
        leaveApplication.setType(Type.PLANNED_LEAVE);
        leaveApplicationDao.request(leaveApplication);

        LeaveApplication leaveApplication1 = new LeaveApplication();
        leaveApplication1.setReason("bvhfjdkc,dgrifdksmnghtikfdlnjfhgy");

        leaveApplicationDao.updateRequest(leaveApplication1,"11");

        LeaveApplication leaveApplication2 = leaveApplicationDao.getById("11") ;
        Assert.assertEquals(leaveApplication2.getReason(),leaveApplication1.getReason());

        leaveApplicationDao.cancelRequest("11");

    }

    @Test
    public void getAllTest() {

        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId("11");
        leaveApplication.setEmployeeId("AA");
        leaveApplication.setReason("xyz");
        leaveApplicationDao.request(leaveApplication);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<LeaveApplication> leaveApplications = leaveApplicationDao.getAll();
        int size = leaveApplications.size();
        Assert.assertEquals(1,size);

        leaveApplicationDao.cancelRequest("11");
    }

    @Test
    public void getByIdTest()
    {
        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId("11");
        leaveApplication.setEmployeeId("AA");
        leaveApplication.setReason("xyz");
        leaveApplication.setStatus(Status.APPLIED);
        leaveApplicationDao.request(leaveApplication);

        LeaveApplication leaveApplication1 = leaveApplicationDao.getById("11");
        Assert.assertEquals(leaveApplication1.getStatus(),leaveApplication.getStatus());

        leaveApplicationDao.cancelRequest("11");
    }


    @Test
    public void getByEmployeeIDTest()
    {
        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId("11");
        leaveApplication.setEmployeeId("AA");
        leaveApplication.setReason("xyz");
        leaveApplicationDao.request(leaveApplication);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<LeaveApplication> leaveApplications = leaveApplicationDao.getByEmployeeId("AA");
        int size = leaveApplications.size();
        Assert.assertEquals(1,size);
        leaveApplicationDao.cancelRequest("11");
    }


    @Test
    public void checkRequestTest() throws IOException
    {
        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId("11");
        leaveApplication.setEmployeeId("AA");
        leaveApplication.setType(Type.PLANNED_LEAVE);
        leaveApplication.setStatus(Status.APPLIED);
        leaveApplicationDao.request(leaveApplication);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<LeaveApplication> leaveApplications = leaveApplicationDao.checkRequest();
        int size = leaveApplications.size();
        Assert.assertEquals(1,size);

        leaveApplicationDao.cancelRequest("11");
    }

    @Test
    public void approveRequestTest()
    {
        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId("11");
        leaveApplication.setEmployeeId("AA");
        leaveApplication.setReason("xyz");
        leaveApplication.setStatus(Status.APPLIED);
        leaveApplication.setType(Type.PLANNED_LEAVE);
        leaveApplicationDao.request(leaveApplication);

        LeaveApplication leaveApplication1 = new LeaveApplication();
        leaveApplication1.setStatus(Status.APPROVED);

        leaveApplicationDao.updateRequest(leaveApplication1,"11");

        LeaveApplication leaveApplication2 = leaveApplicationDao.getById("11") ;
        Assert.assertEquals(leaveApplication2.getStatus(),leaveApplication1.getStatus());

        leaveApplicationDao.cancelRequest("11");

    }

    @Test
    public void getByDateTest()// TODO: Remaining
    {
        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId("11");
        leaveApplication.setEmployeeId("AA");
        leaveApplication.setReason("xyz");
        leaveApplicationDao.request(leaveApplication);

        LeaveApplication leaveApplication1 = leaveApplicationDao.getById("11");
        Assert.assertEquals(leaveApplication1.getEmployeeId(),leaveApplication.getEmployeeId());

        leaveApplicationDao.cancelRequest("11");

    }
    @Test
    public void declineRequestTest()
    {
        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setId("11");
        leaveApplication.setEmployeeId("AA");
        leaveApplication.setReason("xyz");
        leaveApplication.setStatus(Status.APPLIED);
        leaveApplicationDao.request(leaveApplication);

        LeaveApplication leaveApplication1 = new LeaveApplication();
        leaveApplication1.setStatus(Status.REJECTED);

        leaveApplicationDao.updateRequest(leaveApplication1,"11");

        leaveApplicationDao.cancelRequest("11");

        LeaveApplication leaveApplication2 = leaveApplicationDao.getById("11");
        Assert.assertNull(leaveApplication2);
    }


}
