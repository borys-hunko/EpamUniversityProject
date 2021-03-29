<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <title>Login</title>
    <meta charset="UTF-8">
</head>
<body>
    <span>${faculty.name}</span>
    <span><%=(request.getAttribute("errorMsg") == null) ? "" : request.getAttribute("error")%></span>
    <form action="<c:url value="/applicant/apply"/>" method="post">
    <label for="paid">paid</label>
    <input type="radio" name="education_type" id="paid" value="PAID" checked><br>
    <label for="state_funded">state funded</label>
    <input type="radio" name="education_type" id="state_funded" value="STATE_FUNDED"/><br>
    <c:forEach items="${faculty.requiredSubjects}" var="subject">
        <label for="${subject.name}">${subject.name}</label>
        <input type="number" name="results" id="${subject.name}"/><br>
    </c:forEach>
    <input type="submit">
</form>
</body>
</html>