package ru.kata.spring.boot_security.demo.Util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Component
public class UserValidator implements Validator {

    private final UserServiceImpl usersService;

    public UserValidator(UserServiceImpl usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String email = (String) target;

        if (usersService.getUserByUsername(email) == null) { return;}

        errors.rejectValue("email", "", "Человек с таким e-mail уже существует");
    }
}
