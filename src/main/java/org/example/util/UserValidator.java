package org.example.util;

import org.example.model.User;
import org.example.repository.user.DataJpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final DataJpaUserRepository userDao;

    @Autowired
    public UserValidator(DataJpaUserRepository userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userDao.get(user.getEmail()) != null) {
            errors.rejectValue("email", "", "This email already taken");
        }
    }
}
