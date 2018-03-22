package com.brevitaz.model;

import java.util.Date;
import java.util.List;

public class ErrorDetail
{
    private Date timestamp;
    private String message;
    private String details;
    private List<String> errors;

    public ErrorDetail() {

    }

    public ErrorDetail(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public ErrorDetail(Date timestamp,  String details,List<String> errors) {
        this.timestamp = timestamp;
        this.details = details;
        this.errors=errors;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
