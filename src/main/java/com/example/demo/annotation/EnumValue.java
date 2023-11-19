package com.example.demo.annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValue.Validator.class)
public @interface EnumValue {
    String message() default "枚举校验未通过";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] candidateValue();

    class Validator implements ConstraintValidator<EnumValue, Object> {
        private String[] candidateValue;


        @Override
        public void initialize(EnumValue enumValue) {
            candidateValue = enumValue.candidateValue();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            if (value == null) {
                return Boolean.TRUE;
            }
            if(candidateValue.length == 0) {
                return Boolean.TRUE;
            }
            if(Arrays.asList(candidateValue).contains(value.toString())) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }


    }


}
