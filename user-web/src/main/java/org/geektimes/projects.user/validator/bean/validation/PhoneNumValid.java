package org.geektimes.projects.user.validator.bean.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author xubin
 * @date 2021/3/10 19:23
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumValidAnnotationValidator.class)
public @interface PhoneNumValid {

    String message() default "check param may not be legal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
