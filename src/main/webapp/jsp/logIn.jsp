<%@ include file="/jspf/directories.jspf"%>
        <fmt:setLocale value="${sessionScope.lang}"/>
        <fmt:setBundle basename="lang"/>
<html lang="en">
        <head>
                <title>Login</title>
                <%@ include file="/jspf/headDirectives.jspf"%>
        </head>

<body>
        <%@ include file="/jspf/baseHeader.jspf"%>
        <main class="text-center d-flex justify-content-center">
                <form class="form-signin w-25" action="<%=request.getContextPath()%>/logIn" method="post">
                        <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="index.loginMsg"/></h1>
                        <label class="sr-only" for="emailField"><fmt:message key="index.email"/></label>
                        <input class="form-control" type="text" name="email" id="emailField" required>
                        <label class="sr-only" for="passwordField"><fmt:message key="index.password"/></label>
                        <input class="form-control" type="password" name="password" id="passwordField" required>
                        <br>
                        <button class="btn btn-lg btn-primary btn-block" type="submit"> <fmt:message key="index.login"/> </button>
                        <span><%=(request.getAttribute("error") == null) ? "" : request.getAttribute("error")%></span>
                </form>

        </main>
</body>
</html>