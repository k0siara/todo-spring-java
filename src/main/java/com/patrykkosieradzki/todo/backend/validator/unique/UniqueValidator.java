package com.patrykkosieradzki.todo.backend.validator.unique;

import com.patrykkosieradzki.todo.app.ApplicationContextProvider;
import com.patrykkosieradzki.todo.backend.service.util.FieldValueExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private FieldValueExists service;
    private String fieldName;

    @Override
    public void initialize(Unique unique) {
        service = (FieldValueExists) ApplicationContextProvider.getBean(unique.service());
        fieldName = unique.fieldName();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        return !service.fieldValueExists(fieldName, object);
    }

}
