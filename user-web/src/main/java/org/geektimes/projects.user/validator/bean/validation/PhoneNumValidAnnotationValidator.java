package org.geektimes.projects.user.validator.bean.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author xubin
 * @date 2021/3/10 19:24
 */
public class PhoneNumValidAnnotationValidator implements ConstraintValidator<PhoneNumValid, String> {

    Pattern pattern = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");

    @Override
    public void initialize(PhoneNumValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
