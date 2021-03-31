<%@ include file="/jspf/directories.jspf"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title>Applications</title>
    <%@ include file="/jspf/headDirectives.jspf"%>
</head>
<body>
    <%@ include file="/jspf/applicantLogInedHeader.jspf" %>
    <main class="px-5">
        <h3><fmt:message key="index.applications"/></h3>
        <table class="table">
            <tr>
                <th><fmt:message key="index.faculty"/></th>
                <th><fmt:message key="index.educationType"/></th>
                <th><fmt:message key="index.status"/>tus</th>
            </tr>
            <c:forEach items="${applications}" var="application">
                <tr>
                    <td>${application.faculty.name}</td>
                    <td>${application.typeOfEducation}</td>
                    <td>${application.status}</td>
                    <td>
                        <a href="<c:url value="/applicant/applications/delete?id=${application.id}"/>">
                            <button class="btn btn-primary">delete</button>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </main>
</body>
</html>