package com.example.dogapi.validator;

import com.example.dogapi.request.BreedRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BreedValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return BreedRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BreedRequest bodyReq = (BreedRequest) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "breedName", "error.breedName", "breed name is required");
    }
}
