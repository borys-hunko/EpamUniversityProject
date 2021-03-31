<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>faculties</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
<body>
    <c:out value="${param.page}"/>
    <c:choose>
        <c:when test="${param.page==null}">
            <c:set var="pageNum" value="${1+0}"/>
        </c:when>
        <c:otherwise>
            <fmt:parseNumber var="pageNum" value="${param.page}"/>
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
    <a href="<c:url value="/admin/faculties/add"/>">
        <button>add faculty</button>
    </a>
    <br>
    <div class="dropdown-content">
        <a href="<c:url value="/admin/faculties?sort=name&page=${pageNum}"/>">A-Z</a>
        <a href="<c:url value="/admin/faculties?sort=nameDesc&page=${pageNum}"/>">Z-A</a>
        <a href="<c:url value="/admin/faculties?sort=budgetPlaces&page=${pageNum}"/>">By budget places(from lower to bigger)</a>
        <a href="<c:url value="/admin/faculties?sort=budgetPlacesDesc&page=${pageNum}"/>">By budget places(from bigger to lower)</a>
        <a href="<c:url value="/admin/faculties?sort=totalPlaces&page=${pageNum}"/>">By total places(from lower to bigger)</a>
        <a href="<c:url value="/admin/faculties?sort=totalPlacesDesc&page=${pageNum}"/>">By total places(from bigger to lower)</a>
    </div>
    <table border="1px">
        <tr>
            <th>id</th>
            <th>name</th>
            <th>budget places</th>
            <th>total places</th>
        </tr>
    <c:forEach items="${faculties}" var="faculty">
        <tr>
            <td>${faculty.id}</td>
            <td>${faculty.name}</td>
            <td>${faculty.budgetPlaces}</td>
            <td>${faculty.totalPlaces}</td>
            <td>
                <a href="<c:url value="/admin/faculties/update?id=${faculty.id}" />">
                    <button>edit</button>
                </a>
            <td>
            <td>
                <a href="<c:url value="/admin/faculties/delete?id=${faculty.id}"/>">
                    <button>delete</button>
                </a>
            </td>
        </tr>
    </c:forEach>
    </table>
    <%--            pages--%>
    <c:out value="${pageNum}"/>
    <c:out value="qty ${pageQty}"/>
    <c:forEach begin="1" end="${pageQty}" varStatus="loop">
        <a href="<c:url value="/admin/faculties?sort=${sort}&page=${loop.index}"/>">|${loop.index}|</a>
    </c:forEach>
</body>
</html>