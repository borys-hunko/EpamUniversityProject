<%@ include file="/jspf/directories.jspf"%>

<html lang="en">
<head>
    <title>Apply</title>
    <meta charset="UTF-8">
    <%@ include file="/jspf/headDirectives.jspf"%>
</head>
<body>
    <%@ include file="/jspf/applicantLogInedHeader.jspf" %>

    <h4 class="px-3">${faculty.name}</h4>
    <span><%=(request.getAttribute("errorMsg") == null) ? "" : request.getAttribute("error")%></span>
    <form class="px-5" action="<c:url value="/applicant/apply"/>" method="post">
        <div class="form-group">
            <input type="radio" name="education_type" id="paid" value="PAID" checked>
            <label for="paid">paid</label><br>
            <input type="radio" name="education_type" id="state_funded" value="STATE_FUNDED"/>
            <label for="state_funded">state funded</label>
        </div>
        <c:forEach items="${faculty.requiredSubjects}" var="subject">
            <div class="form-group">
                <label for="${subject.name}">${subject.name}</label>
                <input  class="form-control" type="number" name="results" id="${subject.name}"/><br>
            </div>
        </c:forEach>
        <input class="btn btn-primary" type="submit">
    </form>
</body>
</html>