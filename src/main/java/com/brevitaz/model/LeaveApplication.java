package com.brevitaz.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.DateTime;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@EntityScan
public class LeaveApplication
{
    private String id;
    private String employeeId;
    private String employeeName;
    private String reason;
    private Date fromDate;
    private Date toDate;
    private Type type;
    private Status status;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
   // List<HandleRequest> approvals;// TODO :  what is  the appropriate name for this.
    private HandleRequest hr;
    private HandleRequest admin;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

   /* public List<HandleRequest> getApprovals() {
        return approvals;
    }

    public void setApprovals(List<HandleRequest> handleRequests) {
        this.approvals = handleRequests;
    }*/

    public HandleRequest getHr() {
        return hr;
    }

    public void setHr(HandleRequest hr) {
        this.hr = hr;
    }

    public HandleRequest getAdmin() {
        return admin;
    }

    public void setAdmin(HandleRequest admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "LeaveApplication{" +
                "id='" + id + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", reason='" + reason + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", type=" + type +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", hr=" + hr +
                ", admin=" + admin +
                '}';
    }
}
