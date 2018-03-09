package com.brevitaz.controller;

import com.brevitaz.dao.LeavePolicyRuleDao;
import com.brevitaz.model.LeavePolicyRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/leave-policy-rules")
public class LeavePolicyRuleController {

    @Autowired
    private LeavePolicyRuleDao leavePolicyRuleDao;

    @RequestMapping(value = "" , method = RequestMethod.POST)
    public boolean create(@RequestBody LeavePolicyRule leavePolicyRule) {
        return leavePolicyRuleDao.create(leavePolicyRule);
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.PUT)
    public boolean update(@RequestBody LeavePolicyRule leavePolicyRule,@PathVariable String id)   {
        return leavePolicyRuleDao.update(leavePolicyRule,id);
    }
    @RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
    public boolean delete(@PathVariable String id)   {
        return leavePolicyRuleDao.delete(id);
    }

    @RequestMapping(value = "/{id}" , method = RequestMethod.GET) //TODO : not required
    public LeavePolicyRule getById(@PathVariable String id)   {
        return leavePolicyRuleDao.getById(id);
    }

    @RequestMapping(value = "" , method = RequestMethod.GET)
    public List<LeavePolicyRule> getAll()   {
        return leavePolicyRuleDao.getAll();
    }
}
