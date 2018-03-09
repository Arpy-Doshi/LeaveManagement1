package com.brevitaz.dao;

import com.brevitaz.model.LeavePolicy;
import com.brevitaz.model.LeavePolicyRule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LeavePolicyDaoTest {
    @Autowired
    LeavePolicyDao leavePolicyDao;

    @Test
    public void createTest() {
        LeavePolicyRule leavePolicyRule = new LeavePolicyRule();
        leavePolicyRule.setId("1");
        leavePolicyRule.setName("Abc");
        leavePolicyRule.setDescription("fcgtyuijklmnjbhvg");

        List<LeavePolicyRule> leavePolicyRules = new ArrayList<>();
        leavePolicyRules.add(leavePolicyRule);

        LeavePolicy leavePolicy = new LeavePolicy();
        leavePolicy.setId("1");
        leavePolicy.setLeavePolicyRules(leavePolicyRules);

        leavePolicyDao.create(leavePolicy);

        LeavePolicy leavePolicy1 = leavePolicyDao.getById("1");

        Assert.assertEquals(leavePolicy1.getLeavePolicyRules(),leavePolicy.getLeavePolicyRules());

        leavePolicyDao.delete("1");
    }


    @Test
    public void getAllTest()
    {
        LeavePolicyRule leavePolicyRule1 = new LeavePolicyRule();
        leavePolicyRule1.setId("1");
        leavePolicyRule1.setName("Abc");
        leavePolicyRule1.setDescription("fcgtyuijklmnjbhvg");

        List<LeavePolicyRule> leavePolicyRules = new ArrayList<>();
        leavePolicyRules.add(leavePolicyRule1);

        LeavePolicy leavePolicy = new LeavePolicy();
        leavePolicy.setId("1");
        leavePolicy.setLeavePolicyRules(leavePolicyRules);

        leavePolicyDao.create(leavePolicy);

        try{
            Thread.sleep(500);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        List<LeavePolicy> leavePolicies = leavePolicyDao.getAll();
        int size = leavePolicies.size();
        Assert.assertEquals(1,size);

    }

    @Test
    public void updateTest()
    {
        LeavePolicyRule leavePolicyRule = new LeavePolicyRule();
        leavePolicyRule.setId("1");
        leavePolicyRule.setName("Abc");
        leavePolicyRule.setDescription("fcgtyuijklmnjbhvg");

        List<LeavePolicyRule> leavePolicyRules = new ArrayList<>();
        leavePolicyRules.add(leavePolicyRule);

        LeavePolicy leavePolicy = new LeavePolicy();
        leavePolicy.setId("1");
        leavePolicy.setLeavePolicyRules(leavePolicyRules);

        leavePolicyDao.create(leavePolicy);

        LeavePolicyRule leavePolicyRule1 = new LeavePolicyRule();
        leavePolicyRule1.setId("1");
        leavePolicyRule1.setName("CDE");
        leavePolicyRule1.setDescription("Hello");


        List<LeavePolicyRule> leavePolicyRules1 = new ArrayList<>();
        leavePolicyRules1.add(leavePolicyRule1);

        LeavePolicy leavePolicy1 = new LeavePolicy();
        leavePolicy1.setLeavePolicyRules(leavePolicyRules1);

        leavePolicyDao.update(leavePolicy1,"1");

        LeavePolicy leavePolicy2 = leavePolicyDao.getById("1");

        Assert.assertEquals(leavePolicy2.getLeavePolicyRules(),leavePolicy1.getLeavePolicyRules());
    }
    @Test
    public void deleteTest()
    {
        LeavePolicyRule leavePolicyRule = new LeavePolicyRule();
        leavePolicyRule.setId("1");
        leavePolicyRule.setName("Mno");
        leavePolicyRule.setDescription("fcgtyuijklmnjbhvg");

        List<LeavePolicyRule> leavePolicyRules = new ArrayList<>();
        leavePolicyRules.add(leavePolicyRule);

        LeavePolicy leavePolicy = new LeavePolicy();
        leavePolicy.setId("1");
        leavePolicy.setLeavePolicyRules(leavePolicyRules);

        leavePolicyDao.create(leavePolicy);

        leavePolicyDao.delete("1");

        LeavePolicy leavePolicy1 = leavePolicyDao.getById("1");

        Assert.assertNull(leavePolicy1);

    }

    @Test
    public void getByIdTest()
    {
        LeavePolicyRule leavePolicyRule = new LeavePolicyRule();
        leavePolicyRule.setId("1");
        leavePolicyRule.setName("Mno");
        leavePolicyRule.setDescription("fcgtyuijklmnjbhvg");

        List<LeavePolicyRule> leavePolicyRules = new ArrayList<>();
        leavePolicyRules.add(leavePolicyRule);

        LeavePolicy leavePolicy = new LeavePolicy();
        leavePolicy.setId("1");
        leavePolicy.setLeavePolicyRules(leavePolicyRules);

        leavePolicyDao.create(leavePolicy);

        LeavePolicy leavePolicy1 = leavePolicyDao.getById("1");

        Assert.assertEquals(leavePolicy1.getLeavePolicyRules(),leavePolicy.getLeavePolicyRules());


    }
}
