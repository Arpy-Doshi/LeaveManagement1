package com.brevitaz.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtil
{
    public static List<String> fromBindingErrors(Errors errors) {
       /* ObjectError objectError = null;
        String validError = objectError.getDefaultMessage();
        return validError;
*/
        List<String> validErrors = new ArrayList<String>();
        for (ObjectError objectError : errors.getAllErrors()) {
            validErrors.add(objectError.getDefaultMessage());
        }
        return validErrors;
    }
}
