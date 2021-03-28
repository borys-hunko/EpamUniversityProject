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
        <tr>
            <th>id</th>
            <th>name</th>
            <th>budget places</th>
            <th>total places</th>
        </tr>
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