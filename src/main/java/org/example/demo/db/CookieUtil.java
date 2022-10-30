package org.example.demo.db;

import javax.servlet.http.Cookie;

public class CookieUtil {

    protected static String findCookie(Cookie[] cookies, String value) {
        if (cookies != null)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(value)) {
                    return cookie.getValue();
            }
        }

        return null;
    }
}
