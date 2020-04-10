package com.service;

import com.entities.UserEntity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MaxSizeConstraintValidator implements ConstraintValidator<MaxSizeConstraint, List<UserEntity>> {
    @Override
    public boolean isValid(List<UserEntity> values, ConstraintValidatorContext context) {
        return values.size() <= 4;
    }
}
