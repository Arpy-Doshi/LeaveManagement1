package com.brevitaz.errors;

import com.brevitaz.model.LeaveApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LeaveApplicationNotFoundException extends RuntimeException {

    public LeaveApplicationNotFoundException(String msg)
    {
        super(msg);
    }

}
