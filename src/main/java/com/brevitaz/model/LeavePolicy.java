package com.brevitaz.model;

import java.util.List;

public class LeavePolicy
{
    private String id;
    private String name;
    private List<LeavePolicyRule> leavePolicyRules;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LeavePolicyRule> getLeavePolicyRules() {
        return leavePolicyRules;
    }

    public void setLeavePolicyRules(List<LeavePolicyRule> leavePolicyRules) {
        this.leavePolicyRules = leavePolicyRules;
    }

    @Override
    public String toString() {
        return "LeavePolicy{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", leavePolicyRules=" + leavePolicyRules +
                '}';
    }


}
