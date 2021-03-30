<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Users</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <h2>Users</h2>
        <div class="dropdown-content">
            <a href="<c:url value="/admin/users?sort=name"/>">By name(A-Z)</a>
            <a href="<c:url value="/admin/users?sort=nameDesc"/>">By name(Z-A)</a>
            <a href="<c:url value="/admin/users?sort=email"/>">By email(A-Z)</a>
            <a href="<c:url value="/admin/users?sort=emailDesc"/>">By email(Z-A)</a>
        </div>
        <table border="1px">
            <tr>
                <th>id</th>
                <th>email</th>
                <th>role</th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                    <td>
                        <c:choose>
                            <c:when test="${user.isBlocked()}">
                                <a href="/admin/users/setBlocked?id=${user.id}"><button>unblock</button></a>
                            </c:when>
                            <c:otherwise>
                                <a href="/admin/users/setBlocked?id=${user.id}"><button>block</button></a>
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td><a href="/admin/users/details?id=${user.id}"><button>details</button></a></td>
                    <td><a href="/admin/users/admin?id=${user.id}"><button>give admin rights</button></a></td>

                </tr>
            </c:forEach>
        </table>
    </body>
    </html>