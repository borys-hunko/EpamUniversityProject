<%@ include file="/jspf/directories.jspf"%>
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
            <form class="w-50 form-signin"  action="<c:url value="/admin/faculties/add"/>" method="post">
                <label class="sr-only" for="name"><fmt:message key="index.name"/></label>
                <input pattern="[A-Za-z&#1040;-&#1071;&#1072;-&#1103;&#1025;&#1105;]+$" class="form-control" type="text" name="name" id="name"/><br>
                <label  class="sr-only" for="budgePlaces"><fmt:message key="index.budgetPlaces"/></label>
                <input pattern="[0-9]{,3}" class="form-control" type="number" name="budgetPlaces" id="budgePlaces"/><br>
                <label class="sr-only" for="totalPlaces"><fmt:message key="index.allPlaces"/></label>
                <input pattern="[0-9]{,3}" class="form-control" type="number" name="totalPlaces" id="totalPlaces"/><br>
                <label class="sr-only"><fmt:message key="index.subjects"/>:</label><br>
                <c:forEach items="${subjects}" var="subject">
                    <label for="subject">${subject.name}</label>
                    <input  type="checkbox" name="subjects" value="${subject.id}"/><br>
                </c:forEach>
                <br>
                <input class="btn btn-primary" type="submit"/>
            </form>
        </main>

    </body>
</html>