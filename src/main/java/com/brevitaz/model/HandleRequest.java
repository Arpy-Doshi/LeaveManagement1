package com.brevitaz.model;


import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.validation.constraints.NotNull;

@EntityScan
public class HandleRequest
{
    private String employeeId;

    private Status status;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        employeeId = employeeId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HandleRequest{" +
                "EmployeeId='" + employeeId + '\'' +
                ", status=" + status +
                '}';
    }
}
