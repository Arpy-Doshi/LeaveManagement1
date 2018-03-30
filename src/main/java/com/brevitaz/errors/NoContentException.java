package com.brevitaz.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT) //TODO : need to ask.
public class NoContentException extends RuntimeException{


    public NoContentException(String msg)
    {
        super(msg);
    }
}
