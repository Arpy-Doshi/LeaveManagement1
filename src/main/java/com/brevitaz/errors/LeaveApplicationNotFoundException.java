package com.brevitaz.errors;

import com.brevitaz.model.LeaveApplication;

public class LeaveApplicationNotFoundException extends RuntimeException {

    public LeaveApplicationNotFoundException(String msg)
    {
        super(msg);
    }

}
