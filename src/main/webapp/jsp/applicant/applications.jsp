<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>my applications</title>
</head>
<body>
<%--<a href="<c:url value="/admin/faculties/add"/>">--%>
<%--<button>add faculty</button>--%>
<%--</a>--%>
<%--<br>--%>
<table border="1px">
    <tr>
        <th>Faculty</th>
        <th>Education type</th>
        <th>Status</th>
        <th>Priority</th>
    </tr>
    <c:forEach items="${applications}" var="application">
        <tr>
            <td>${application.faculty.name}</td>
            <td>${application.typeOfEducation}</td>
            <td>${application.status}</td>
            <td>
                <a href="<c:url value="/applicant/applications/update?id=${application.id}" />">
                    <button>edit</button>
                </a>
            </td>
            <td>
                <a href="<c:url value="/applicant/applications/delete?id=${application.id}"/>">
                <button>delete</button>
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>