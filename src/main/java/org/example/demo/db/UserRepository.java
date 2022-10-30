package org.example.demo.db;

import org.example.demo.entity.User;
import org.example.demo.service.UserService;

import javax.servlet.http.Cookie;
import java.sql.*;

public class UserRepository {
    public static final UserRepository USER_REPOSITORY = new UserRepository();

    public User getUser(String login) throws SQLException, ClassNotFoundException {
        User user = null;
        UserService us = new UserService();

        return us.getByLogin(login);
    }

    public void insertUser(User user) throws SQLException, ClassNotFoundException {
        UserService us = new UserService();
        us.add(user);
    }

    public User getUserByCookies(Cookie[] cookies) throws SQLException, ClassNotFoundException {
        String login = CookieUtil.findCookie(cookies, "login");
        User user;
        if (login == null || (user = getUser(login)) == null) {
            return null;
        }

        return user;
    }
}
