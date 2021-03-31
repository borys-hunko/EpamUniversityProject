<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Users</title>
        <meta charset="UTF-8">
    </head>
    <body>
    <c:choose>
        <c:when test="${param.page==null}">
            <c:set var="pageNum" value="${1+0}"/>
        </c:when>
        <c:otherwise>
            <fmt:parseNumber var="pageNum" integerOnly="true" value="${param.page}"/>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${param.sort==null}">
            <c:set var="sort" value="name"/>
        </c:when>
        <c:otherwise>
            <c:set var="sort" value="${param.sort}"/>
        </c:otherwise>
    </c:choose>
        <h2>Users</h2>
        <div class="dropdown-content">
            <a href="<c:url value="/admin/users?sort=name&page=${pageNum}"/>">By name(A-Z)</a>
            <a href="<c:url value="/admin/users?sort=nameDesc&page=${pageNum}"/>">By name(Z-A)</a>
            <a href="<c:url value="/admin/users?sort=email&page=${pageNum}"/>">By email(A-Z)</a>
            <a href="<c:url value="/admin/users?sort=emailDesc&page=${pageNum}"/>">By email(Z-A)</a>
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
    <%--            pages--%>
    <c:if test="${pageNum!=1}">
        <a href="<c:url value="/admin/users?sort=${sort}&page=${pageNum-1}"/>">< </a>
    </c:if>

    <c:forEach begin="1" end="${pageQty}" varStatus="loop">
        <a href="<c:url value="/admin/users?sort=${sort}&page=${loop.index}"/>">|${loop.index}|</a>
    </c:forEach>

    <c:if test="${pageNum!=pageQty}">
        <a href="<c:url value="/admin/users?sort=${sort}&page=${pageNum+1}"/>">&#62;</a>
    </c:if>
    </body>
    </html>