package org.example.demo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user;
        try {
            user = UserRepository.USER_REPOSITORY.getUserByCookies(req.getCookies());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("contextPath", req.getContextPath());
        if (user != null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("authentication/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        req.setAttribute("contextPath", req.getContextPath());

        if (login == null || password == null) {
            return;
        }


        User user;
        try {
            user = UserRepository.USER_REPOSITORY.getUser(login);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (user == null || !user.getPassword().equals(password)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        resp.addCookie(new Cookie("login", login));
        resp.addCookie(new Cookie("password", password));

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
