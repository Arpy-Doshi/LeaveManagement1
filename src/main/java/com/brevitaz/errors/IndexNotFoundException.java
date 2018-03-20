package com.brevitaz.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class IndexNotFoundException extends NullPointerException {
    public IndexNotFoundException() {
    }

    public IndexNotFoundException(String msg) {
        super(msg);
    }
}
