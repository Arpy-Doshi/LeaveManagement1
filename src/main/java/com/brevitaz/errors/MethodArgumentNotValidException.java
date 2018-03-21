package com.brevitaz.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MethodArgumentNotValidException extends IllegalArgumentException{

    public MethodArgumentNotValidException() {
    }

    public MethodArgumentNotValidException (String msg)
    {
            super(msg);
    }
}
