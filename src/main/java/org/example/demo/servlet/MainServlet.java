package org.example.demo.servlet;

import org.example.demo.db.UserRepository;
import org.example.demo.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainServlet extends HttpServlet {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user;
        try {
            user = UserRepository.USER_REPOSITORY.getUserByCookies(req.getCookies());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("date", DATE_FORMAT.format(new Date()));
        req.setAttribute("contextPath", req.getContextPath());
        String path = req.getParameter("path");

        if (path == null || !path.contains(user.getLogin())) {
            path = System.getProperty("os.name").toLowerCase().startsWith("win") ? "C:/": "/tmp/";
            path += user.getLogin();
        }
        path = path.replaceAll("%20", " ");

        req.setAttribute("path", path);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (dir.isDirectory()) {
            getFiles(req, dir);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("explore.jsp");
            requestDispatcher.forward(req, resp);
        }
        else {
            downloadFile(resp, dir);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }

        resp.sendRedirect(req.getContextPath() + "/login");
    }

    private void getFiles(HttpServletRequest req, File file) {
        File[] arrFiles = file.listFiles();
        if (arrFiles == null) {
            return;
        }
        List<File> lst = Arrays.asList(arrFiles);
        req.setAttribute("elements", lst);
    }

    private void downloadFile(HttpServletResponse resp, File file) {
        resp.setContentType("text/plain");
        resp.setHeader("Content-disposition", "attachment; filename=" + file.getName());

        try (InputStream in = new FileInputStream(file); OutputStream out = resp.getOutputStream()) {
            byte[] buffer = new byte[1048];

            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
