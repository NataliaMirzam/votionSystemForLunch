package nataliamirzam.util;

import nataliamirzam.model.Role;
import nataliamirzam.model.User;
import nataliamirzam.to.UserTo;

import java.util.Collections;


public class UsersUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Collections.singleton(Role.USER));
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}