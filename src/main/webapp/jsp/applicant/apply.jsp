<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <title>Login</title>
    <meta charset="UTF-8">
</head>
<body>
<span>Sign up</span>
<span color="red"><%=(request.getAttribute("error") == null) ? "" : request.getAttribute("error")%></span>
<form action="<c:url value="/applicant/apply"/>" method="post">
    <label for="priority">priority</label>
    <input type="number" name="priority" id="priority"><br>
    <c:forEach items="${faculty.requiredSubjects}" var="subject">
        <label>${subject.name}</label>
        <input type="number" name="results"/>
    </c:forEach>
    <input type="submit">
</form>
</body>
</html>