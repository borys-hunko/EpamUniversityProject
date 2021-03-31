<%@ include file="/jspf/directories.jspf"%>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="lang"/>
<html>
<head>
    <title>Apply</title>
    <meta charset="UTF-8">
    <%@ include file="/jspf/headDirectives.jspf"%>
</head>
<body>
    <%@ include file="/jspf/applicantLogInedHeader.jspf" %>

    <h4 class="px-3">${faculty.name}</h4>
    <span style="color:red"><%=(request.getAttribute("errorMsg") == null) ? "" : request.getAttribute("error")%></span>
    <form class="px-5" action="<c:url value="/applicant/apply"/>" method="post">
        <div class="form-group">
            <input type="radio" name="education_type" id="paid" value="PAID" checked>
            <label for="paid"><fmt:message key="applicant.paid"/></label><br>
            <input type="radio" name="education_type" id="state_funded" value="STATE_FUNDED"/>
            <label for="state_funded"><fmt:message key="applicant.stateFunded"/></label>
        </div>
        <c:forEach items="${faculty.requiredSubjects}" var="subject">
            <div class="form-group">
                <label for="${subject.name}">${subject.name}</label>
                <input pattern="[0-9]{,3}" class="form-control" type="number" name="results" id="${subject.name}"/><br>
            </div>
        </c:forEach>
        <input class="btn btn-primary" type="submit">
    </form>
</body>
</html>