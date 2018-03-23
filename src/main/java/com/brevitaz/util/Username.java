package com.brevitaz.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {

    String message() default "{Username should be valid!!}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}