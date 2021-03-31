<%@ include file="/jspf/directories.jspf"%>
<html lang="en">
<head>
    <title>Applications</title>
    <%@ include file="/jspf/headDirectives.jspf"%>
</head>
<body>
    <%@ include file="/jspf/applicantLogInedHeader.jspf" %>
    <main class="px-5">
        <h3>My applications</h3>
        <table class="table">
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