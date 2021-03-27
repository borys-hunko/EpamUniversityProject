<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>faculties</title>
</head>
<body>
    <a href="<c:url value="/admin/faculties/add"/>">
        <button>add faculty</button>
    </a>
    <br>
    <table border="1px">
        <tr>id</tr>
        <tr>name</tr>
        <tr>budget places</tr>
        <tr>total places</tr>
        <c:forEach items="${faculties}" var="faculty">
            <tr>
                <td>${faculty.id}</td>
                <td>${faculty.name}</td>
                <td>${faculty.budgetPlaces}</td>
                <td>${faculty.totalPlaces}</td>
                <td>
                    <a href="<c:url value="/admin/faculties/update?id=${faculty.id}" />">
                        <button>edit</button>
                    </a>
                <td>
                <td>
                    <a href="<c:url value="/admin/faculties/delete?id=${faculty.id}"/>">
                        <button>delete</button>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>