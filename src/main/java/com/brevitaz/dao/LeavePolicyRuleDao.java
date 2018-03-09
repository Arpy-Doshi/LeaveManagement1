package com.brevitaz.dao;

import com.brevitaz.model.LeavePolicyRule;

import java.util.List;

public interface LeavePolicyRuleDao
{
    public boolean create(LeavePolicyRule leavePolicyRule);
    public boolean update(LeavePolicyRule leavePolicyRule,String id);
    public boolean delete( String id);
    public LeavePolicyRule getById(String id);
    public List<LeavePolicyRule> getAll();

}
