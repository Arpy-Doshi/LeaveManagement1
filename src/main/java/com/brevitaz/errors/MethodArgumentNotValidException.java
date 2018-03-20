package com.brevitaz.errors;

public class MethodArgumentNotValidException extends IllegalArgumentException{

    public MethodArgumentNotValidException() {
    }

    public MethodArgumentNotValidException (String msg)
    {
            super(msg);
    }
}
