<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<html lang="en">
<head>
    <title>Login</title>
    <meta charset="UTF-8">
</head>
<span>Sign up</span>
<span><%=(request.getAttribute("error") == null) ? "" : request.getAttribute("error")%></span>
<form action="<%=request.getContextPath()%>/signUp" method="post">
    <label for="email">email</label>
    <input type="email" name="email" id="email" placeholder="Enter email"><br>
    <label for="password">password</label>
    <input type="password" name="password" id="password" placeholder="password"><br>
    <label for="firstName">first name</label>
    <input type="text" name="firstName" id="firstName"><br>
    <label for="lastName">second name</label>
    <input type="text" name="lastName" id="lastName"><br>
    <label for="fathersName">fathers name</label>
    <input type="text" name="fathersName" id="fathersName"><br>
    <label for="region">region</label>
    <input type="text" name="region" id="region"><br>
    <label for="city">city</label>
    <input type="text" name="city" id="city"><br>
    <label for="school">school</label>
    <input type="text" name="school" id="school"><br>
    <input type="submit">
</form>
</html>