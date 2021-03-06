package com.brevitaz.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@EntityScan
public class LeavePolicy
{
    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private List<LeavePolicyRule> leavePolicyRules;

    private LPStatus status;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;

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

    public LPStatus getStatus() {
        return status;
    }

    public void setStatus(LPStatus status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "LeavePolicy{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", leavePolicyRules=" + leavePolicyRules +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
