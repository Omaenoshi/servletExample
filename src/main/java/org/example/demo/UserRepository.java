package org.example.demo;

import javax.servlet.http.Cookie;
import java.sql.*;

public class UserRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/filemanager";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1010";
    public static final UserRepository USER_REPOSITORY = new UserRepository();


    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    protected User getUser(String login) throws SQLException, ClassNotFoundException {
        User user = null;
        try (Connection conn = getConnection()) {
            String sql = "SELECT password, email FROM users WHERE login = ?;";
            try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    String password = resultSet.getString(1);
                    String email = resultSet.getString(2);
                    user = new User(login, password, email);
                }
            }
        }

        return user;
    }

    protected void insertUser(User user) throws SQLException, ClassNotFoundException {
        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO users (login, password, email) VALUES (?, ?, ?)";
            try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());

                preparedStatement.executeUpdate();
            }
        }
    }

    protected User getUserByCookies(Cookie[] cookies) throws SQLException, ClassNotFoundException {
        String login = CookieUtil.findCookie(cookies, "login");
        User user;
        if (login == null || (user = getUser(login)) == null) {
            return null;
        }

        return user;
    }
}
