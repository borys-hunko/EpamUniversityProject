<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <title>Login</title>
    <meta charset="UTF-8">
</head>
<body>
<span>Sign up</span>
<span><%=(request.getAttribute("error") == null) ? "" : request.getAttribute("error")%></span>
<form action="<c:url value="/apply"/>" method="post">
    <label for="priority">priority</label>
    <input type="number" name="priority" id="priority"><br>
    <label for="subj1">${faculty.requiredSubjects.get(0)}</label>
    <input type="number" name="subj1" id="subj1"><br>
    <label for="subj2">${faculty.requiredSubjects.get(1)}</label>
    <input type="number" name="subj2" id="subj2"><br>
    <label for="subj3">${faculty.requiredSubjects.get(2)}</label>
    <input type="number" name="subj3" id="subj3"><br>
    <input type="submit">
</form>
</body>
</html>