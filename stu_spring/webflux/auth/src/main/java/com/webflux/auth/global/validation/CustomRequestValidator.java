//package com.webflux.auth.global.validation;
//
//import com.webflux.auth.domain.user.payload.request.CreateUserRequest;
//import org.springframework.validation.Errors;
//import org.springframework.validation.ValidationUtils;
//import org.springframework.validation.Validator;
//
//public class CustomRequestValidator implements Validator {
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return CreateUserRequest.class.isAssignableFrom(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Email Required");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Password Required");
//        CreateUserRequest request =
//
//    }
//}
