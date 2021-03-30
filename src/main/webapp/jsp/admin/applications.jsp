<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Users</title>
<meta charset="UTF-8">
</head>
<body>
    <h2>Applications</h2>
    <div class="dropdown-content">
        <a href="<c:url value="/admin/users?sort=name"/>">By email(A-Z)</a>
        <a href="<c:url value="/admin/users?sort=nameDesc"/>">By email(Z-A)</a>
        <a href="<c:url value="/admin/users?sort=email"/>">By faculty(A-Z)</a>
        <a href="<c:url value="/admin/users?sort=emailDesc"/>">By faculty(Z-A)</a>
    </div>
    <table border="1px">
        <tr>
            <th>id</th>
            <th>user email</th>
            <th>faculty name</th>
            <th>status</th>
        </tr>
        <c:forEach items="${applications}" var="application">
            <tr>
                <td>${application.id}</td>
                <td>${application.applicant.email}</td>
                <td>${application.faculty.name}</td>
                <td>${application.status}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>