package com.brevitaz.dao;

import com.brevitaz.model.LeavePolicyRule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LeavepolicyRuleDaoTest {
    @Autowired
    LeavePolicyRuleDao leavePolicyRuleDao;

    @Test
    public void createTest() {
        LeavePolicyRule leavePolicyRule = new LeavePolicyRule();
        leavePolicyRule.setId("11");
        leavePolicyRule.setName("Abc");
        leavePolicyRule.setDescription("fcgtyuijklmnjbhvg");

        leavePolicyRuleDao.create(leavePolicyRule);

        LeavePolicyRule leavePolicyRule1 = leavePolicyRuleDao.getById("11");
        Assert.assertEquals(leavePolicyRule1.getName(),leavePolicyRule.getName());

        leavePolicyRuleDao.delete("11");
    }

    @Test
    public void getAllTest() {
        LeavePolicyRule leavePolicyRule = new LeavePolicyRule();
        leavePolicyRule.setId("11");
        leavePolicyRule.setName("Abc");
        leavePolicyRule.setDescription("fcgtyuijklmnjbhvg");

        leavePolicyRuleDao.create(leavePolicyRule);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<LeavePolicyRule> leavePolicyRules = leavePolicyRuleDao.getAll();
        int size = leavePolicyRules.size();
        Assert.assertEquals(1,size);

        leavePolicyRuleDao.delete("11");
    }

    @Test
    public void updateTest() {
        LeavePolicyRule leavePolicyRule = new LeavePolicyRule();
        leavePolicyRule.setId("11");
        leavePolicyRule.setName("Abc");
        leavePolicyRule.setDescription("fcgtyuijklmnjbhvg");

        leavePolicyRuleDao.create(leavePolicyRule);

        LeavePolicyRule leavePolicyRule2 = new LeavePolicyRule();
        leavePolicyRule2.setName("Def");
        leavePolicyRuleDao.update(leavePolicyRule2,"11");

        LeavePolicyRule leavePolicyRule1 = leavePolicyRuleDao.getById("11");
        Assert.assertEquals(leavePolicyRule1.getName(),leavePolicyRule2.getName());

        leavePolicyRuleDao.delete("11");
    }

    @Test
    public void deleteTest() {
        LeavePolicyRule leavePolicyRule = new LeavePolicyRule();
        leavePolicyRule.setId("11");
        leavePolicyRule.setName("Abc");
        leavePolicyRule.setDescription("fcgtyuijklmnjbhvg");

        leavePolicyRuleDao.create(leavePolicyRule);
        leavePolicyRuleDao.delete("11");

        LeavePolicyRule leavePolicyRule1 = leavePolicyRuleDao.getById("11");
        Assert.assertNull(leavePolicyRule1);
    }

    @Test
    public void getByIdTest() {
      LeavePolicyRule leavePolicyRule = new LeavePolicyRule();
        leavePolicyRule.setId("11");
        leavePolicyRule.setName("Abc");
        leavePolicyRule.setDescription("fcgtyuijklmnjbhvg");

        leavePolicyRuleDao.create(leavePolicyRule);

        LeavePolicyRule leavePolicyRule1 = leavePolicyRuleDao.getById("11");
        Assert.assertEquals(leavePolicyRule1.getName(),leavePolicyRule.getName());

        leavePolicyRuleDao.delete("11");

      }
}