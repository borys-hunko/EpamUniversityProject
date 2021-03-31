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
    <main class="mx-5">
        <h3>Sign up</h3>
        <span style="clor:red"><%=(request.getAttribute("error") == null) ? "" : request.getAttribute("error")%></span>
        <form class="w-50 form-signin" action="<%=request.getContextPath()%>/signUp" method="post">
        <label pattern="/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/" class="sr-only" for="email"><fmt:message key="index.email"/></label>
        <input class="form-control" id="email" name="email" placeholder="Enter email" type="email"><br>
        <label class="sr-only" for="password"><fmt:message key="index.password"/></label>
        <input class="form-control" id="password" name="password" placeholder="Enter password" type="password"><br>
        <label class="sr-only" for="firstName"><fmt:message key="index.firstName"/></label>
        <input pattern="[A-Za-z&#1040;-&#1071;&#1072;-&#1103;&#1025;&#1105;]+$" class="form-control" id="firstName" name="firstName" placeholder="Enter first name"><br>
        <label class="sr-only" for="lastName"><fmt:message key="index.lastName"/></label>
        <input pattern="[A-Za-z&#1040;-&#1071;&#1072;-&#1103;&#1025;&#1105;]+$" class="form-control" id="lastName" name="lastName" placeholder="Enter last name" ><br>
        <label class="sr-only" for="fathersName"><fmt:message key="index.fathersName"/></label>
        <input pattern="[A-Za-z&#1040;-&#1071;&#1072;-&#1103;&#1025;&#1105;]+$" class="form-control" id="fathersName" name="fathersName" placeholder="Enter fathers name" ><br>
        <label class="sr-only" for="region"><fmt:message key="index.region"/></label>
        <input pattern="[A-Za-z&#1040;-&#1071;&#1072;-&#1103;&#1025;&#1105;]+$" class="form-control" id="region" name="region" placeholder="Enter region" ><br>
        <label class="sr-only" for="city"><fmt:message key="index.city"/></label>
        <input pattern="[A-Za-z&#1040;-&#1071;&#1072;-&#1103;&#1025;&#1105;]+$" class="form-control" id="city" name="city" placeholder="Enter city" ><br>
        <label class="sr-only" for="school"><fmt:message key="index.school"/></label>
        <input pattern="[A-Za-z&#1040;-&#1071;&#1072;-&#1103;&#1025;&#1105;]+$" class="form-control" id="school" name="school" placeholder="Enter school" ><br>
        <input class="btn btn-lg btn-primary btn-block" type="submit">
        </form>
    </main>
</body>
</html>