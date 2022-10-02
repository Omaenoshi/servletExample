<%--
  Created by IntelliJ IDEA.
  User: rassv
  Date: 02.10.2022
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Регистрация</h1>
    <form method="post">
        <p><strong>Логин:</strong>
            <input maxlength="25" size="40" name="login"></p>
        <p><strong>Пароль:</strong>
            <input type="password" maxlength="25" size="40" name="password"></p>
        <p><strong>Электронная почта:</strong>
            <input type="email" maxlength="25" size="40" name="email"></p>
        <button type="submit">Зарегистрироваться</button>
    </form>
</body>
</html>
