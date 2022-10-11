package org.example.demo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserRepository.USER_REPOSITORY.getUserByCookies(req.getCookies());
        if (user != null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("authentication/registration.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if (login == null || password == null || email == null) {
            return;
        }
        if (UserRepository.USER_REPOSITORY.getUserByLogin(login) != null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = new User(login, password, email);
        UserRepository.USER_REPOSITORY.addUserByLogin(user, login);
        resp.addCookie(new Cookie("login", login));
        resp.addCookie(new Cookie("password", password));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
