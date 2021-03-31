<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
<title>add faculty</title>
    <%@ include file="/jspf/headDirectives.jspf"%>
</head>
<body>
    <%@ include file="/jspf/adminHeader.jspf" %>
    <main class="px-5">
        <form class="w-50 form-signin" action="<c:url value="/admin/faculties/update"/>" method="post">
            <label for="name"><fmt:message key="index.name"/></label>
            <input  class="form-control" pattern="[A-Za-z&#1040;-&#1071;&#1072;-&#1103;&#1025;&#1105;]+$" type="text" name="name" id="name" value="${faculty.name}"/><br>
            <label  class="sr-only" for="budgePlaces"><fmt:message key="index.budgetPlaces"/></label>
            <input  class="form-control" pattern="[0-9]{,3}" type="number" name="budgetPlaces" id="budgePlaces" value="${faculty.budgetPlaces}"/><br>
            <label  class="sr-only" for="totalPlaces"><fmt:message key="index.totalPlaces"/></label>
            <input  class="form-control" pattern="[0-9]{,3}" type="number" name="totalPlaces" id="totalPlaces" value="${faculty.totalPlaces}"/><br>
            <label  class="sr-only"><fmt:message key="index.subjects"/>:</label><br>
            <c:forEach items="${subjects}" var="subject">
                <label  class="sr-only" for="subject">${subject.name}</label>
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
    </main>
</body>
</html>