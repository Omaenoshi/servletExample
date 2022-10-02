package org.example.demo;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    public static final UserRepository USER_REPOSITORY = new UserRepository();
    private final Map<String, User> userByLogin = new HashMap<>();

    protected User getUserByCookies(Cookie[] cookies) {
        String login = CookieUtil.findCookie(cookies, "login");
        User user;
        if (login == null || (user = getUserByLogin(login)) == null) {
            return null;
        }

        return user;
    }

    protected User getUserByLogin(String login) {
        if (userByLogin.containsKey(login)) {
            return userByLogin.get(login);
        }

        return null;
    }

    protected void addUserByLogin(User user, String login) {
        userByLogin.put(login, user);
    }
}
