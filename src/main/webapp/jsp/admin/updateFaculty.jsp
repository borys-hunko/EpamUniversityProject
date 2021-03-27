<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>add faculty</title>
<meta charset="UTF-8">
</head>
<body>
    <p>${faculty.requiredSubjects}</p>
    <p>${subjects}</p>
        <form action="<c:url value="/admin/faculties/update"/>" method="post">
                <label for="name">Faculty name</label>
                <input type="text" name="name" id="name" value="${faculty.name}"/><br>
                <label for="budgePlaces">Budget places</label>
                <input type="number" name="budgetPlaces" id="budgePlaces" value="${faculty.budgetPlaces}"/><br>
                <label for="totalPlaces">Total places</label>
                <input type="number" name="totalPlaces" id="totalPlaces" value="${faculty.totalPlaces}"/><br>
                <label>Choose subjects:</label><br>
                <c:forEach items="${subjects}" var="subject">
                        <label for="subject">${subject.name}</label>
                         <c:choose>
                                <c:when test="${faculty.requiredSubjects.contains(subject)}">
                                        <input type="checkbox" name="subjects" value="${subject.id}" checked/><br>
                                </c:when>
                                <c:otherwise>
                                        <input type="checkbox" name="subjects" value="${subject.id}"/><br>
                                </c:otherwise>
                         </c:choose>
                </c:forEach>
                <br>
                <input type="submit"/>
        </form>
</body>
</html>