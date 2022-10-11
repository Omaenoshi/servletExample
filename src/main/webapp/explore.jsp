<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form method="post">
        <input type="submit" name="exit" value="Exit"/>
    </form>
    <p>${date}</p>
    <h1>${path}</h1>
    <p><a href="${contextPath}/files?path=${path.substring(0, path.lastIndexOf("/"))}">Назад</a></p>
    <c:forEach var="el" items="${elements}">
        <p><a href="${contextPath}/files?path=${el.getAbsolutePath().replace("\\", "/")}">${el.getName()} (${el.isDirectory() ? "D" : "F"})</a></p>
    </c:forEach>
</body>
</html>