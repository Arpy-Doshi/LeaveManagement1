package com.brevitaz.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAllowedException extends RuntimeException{
    public NotAllowedException() {
    }

    public NotAllowedException(String message) {
        super(message);
    }
}
