package org.example.demo.servlet;

import org.example.demo.db.UserRepository;
import org.example.demo.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user;
        try {
            user = UserRepository.USER_REPOSITORY.getUserByCookies(req.getCookies());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (user != null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("authentication/registration.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if (login == null || password == null || email == null) {
            return;
        }
        try {
            if (UserRepository.USER_REPOSITORY.getUser(login) != null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        User user = new User(login, password, email);
        try {
            UserRepository.USER_REPOSITORY.insertUser(user);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        resp.addCookie(new Cookie("login", login));
        resp.addCookie(new Cookie("password", password));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
