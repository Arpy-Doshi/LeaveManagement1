package com.brevitaz.model;


public class HandleRequest
{
    private String EmployeeId;
    private Status status;

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
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
                "EmployeeId='" + EmployeeId + '\'' +
                ", status=" + status +
                '}';
    }
}
