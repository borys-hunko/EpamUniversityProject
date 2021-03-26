<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<html lang="en">
<head>
    <title>Login</title>
    <meta charset="UTF-8">
</head>
ZX<body>
<span>Sign up</span>
<span><%=(request.getAttribute("error") == null) ? "" : request.getAttribute("error")%></span>
<form action="<%=request.getContextPath()%>/signUp" method="post">
    <label for="email">email</label>
    <input id="email" name="email" placeholder="Enter email" type="email"><br>
    <label for="password">password</label>
    <input id="password" name="password" placeholder="password" type="password"><br>
    <label for="firstName">first name</label>
    <input id="firstName" name="firstName" type="text"><br>
    <label for="lastName">second name</label>
    <input id="lastName" name="lastName" type="text"><br>
    <label for="fathersName">fathers name</label>
    <input id="fathersName" name="fathersName" type="text"><br>
    <label for="region">region</label>
    <input id="region" name="region" type="text"><br>
    <label for="city">city</label>
    <input id="city" name="city" type="text"><br>
    <label for="school">school</label>
    <input id="school" name="school" type="text"><br>
    <input type="submit">
</form>
</body>
</html>