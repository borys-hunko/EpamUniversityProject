<%@ include file="/jspf/directories.jspf"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lang"/>
<html>
<head>
        <title>Home</title>
        <%@ include file="/jspf/headDirectives.jspf"%>

</head>
<body>
        <%@ include file="/jspf/adminHeader.jspf" %>
        <main class="px-5">
                <h3>ADMIN <c:out value="${user.email}"/></h3>
                <a href="<c:url value="/admin/faculties"/>"><fmt:message key="index.faculties"/></a><br>
                <a href="<c:url value="/admin/users"/>"><fmt:message key="admin.users"/></a><br>
                <a href="<c:url value="/admin/finalStatement"/>"><button class="btn btn-primary"><fmt:message key="admin.makeFinalStatement"/></button></a><br>
        </main>
</body>
</html>