package nataliamirzam.web.user;


import nataliamirzam.model.Role;
import nataliamirzam.model.User;
import nataliamirzam.util.JsonUtil;
import nataliamirzam.web.MatcherFactory;

import java.util.Collections;
import java.util.Date;
import java.util.Set;


public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int GUEST_ID = 3;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String GUEST_MAIL = "guest@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Collections.singleton(Role.USER));
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Set.of(Role.ADMIN, Role.USER));
    public static final User guest = new User(GUEST_ID, "Guest", GUEST_MAIL, "guest", null);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), Set.of(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
