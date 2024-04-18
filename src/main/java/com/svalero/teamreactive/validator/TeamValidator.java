package com.svalero.teamreactive.validator;

import com.svalero.teamreactive.domain.Team;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class TeamValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Team.class.isAssignableFrom(clazz);
    }

    //Validar si tiene nombre los objetos y sino dara error
    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
    }
}
