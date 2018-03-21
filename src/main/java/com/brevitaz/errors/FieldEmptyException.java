package com.brevitaz.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FieldEmptyException  extends RuntimeException{
    public FieldEmptyException(String msg)
    {
        super(msg);
    }

}
