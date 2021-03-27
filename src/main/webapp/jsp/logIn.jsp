<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="en">
        <head>
                <title>Login</title>
                <meta charset="UTF-8">
        </head>
<body>
        <span>
        Sign In
        </span>
        <form action="<%=request.getContextPath()%>/logIn" method="post">
                <span>Username</span>
                <input type="text" name="email" placeholder="Enter email">
                <span></span>
                <span>Password</span>
                <input type="password" name="password" placeholder="Enter password">
                <br>
                <button>
                Login
                </button>
                <br>
                <span><%=(request.getAttribute("error") == null) ? "" : request.getAttribute("error")%></span>
        </form>
</body>
</html>