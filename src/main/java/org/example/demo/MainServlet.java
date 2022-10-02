package org.example.demo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainServlet extends HttpServlet {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("date", DATE_FORMAT.format(new Date()));
        String path = req.getParameter("path");
        if (path == null) {
            path = "C:/";
        }
        path = path.replaceAll("%20", " ");
        req.setAttribute("path", path);
        File dir = new File(path);
        if (dir.isDirectory()) {
            getFiles(req, dir);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("explore.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void getFiles(HttpServletRequest req, File file) {
        File[] arrFiles = file.listFiles();
        if (arrFiles == null) {
            return;
        }
        List<File> lst = Arrays.asList(arrFiles);
        req.setAttribute("elements", lst);
    }

}
