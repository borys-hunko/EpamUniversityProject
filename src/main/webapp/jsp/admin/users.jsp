<%@ include file="/jspf/directories.jspf"%>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="lang"/>
<html>
    <head>
        <title>Users</title>
        <%@ include file="/jspf/headDirectives.jspf"%>
    </head>
    <body>
    <%@ include file="/jspf/adminHeader.jspf" %>
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
    <main class="px-5">
        <h3><fmt:message key="admin.users"/></h3>
        <a class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <fmt:message key="index.sort"/>
            </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li><a class="text-decoration-none" href="<c:url value="/admin/users?sort=name&page=${pageNum}"/>"><fmt:message key="sort.byName"/></a></li>
                <li><a class="text-decoration-none" href="<c:url value="/admin/users?sort=nameDesc&page=${pageNum}"/>"><fmt:message key="sort.byNameDesc"/></a></li>
                <li><a class="text-decoration-none" href="<c:url value="/admin/users?sort=email&page=${pageNum}"/>"><fmt:message key="sort.byEmail"/></a></li>
                <li><a class="text-decoration-none" href="<c:url value="/admin/users?sort=emailDesc&page=${pageNum}"/>"><fmt:message key="sort.byEmailDesc"/></a></li>
            </ul>
        </a>
        <table class="table">
            <tr>
                <th>id</th>
                <th><fmt:message key="index.email"/></th>
                <th><fmt:message key="admin.role"/></th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                <td>${user.id}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td>
                <c:choose>
                    <c:when test="${user.isBlocked()}">
                        <a href="/admin/users/setBlocked?id=${user.id}"><button class="btn btn-primary"><fmt:message key="admin.unblock"/></button></a>
                    </c:when>
                    <c:otherwise>
                        <a href="/admin/users/setBlocked?id=${user.id}"><button class="btn btn-primary"><fmt:message key="admin.block"/></button></a>
                    </c:otherwise>
                </c:choose>
                </td>
              </tr>
            </c:forEach>
        </table>
        <%--            pages--%>
    <ul class="pagination start-50 translate-middl">
    <c:if test="${pageNum!=1}">
        <li class="page-item"><a class="page-link" href="<c:url value="/admin/users?sort=${sort}&page=${pageNum-1}"/>">< </a></li>
    </c:if>

    <c:forEach begin="1" end="${pageQty}" varStatus="loop">
        <c:if test="${loop.index!=pageNum}">
            <li class="page-item"><a class="page-link" href="<c:url value="/admin/users?sort=${sort}&page=${loop.index}"/>">${loop.index}</a></li>
        </c:if>
        <c:if test="${loop.index==pageNum}">
            <li class="page-item"><div class="page-link">${loop.index}</div></li>
        </c:if>
    </c:forEach>

    <c:if test="${pageNum!=pageQty}">
        <li class="page-item"><a class="page-link" href="<c:url value="/admin/users?sort=${sort}&page=${pageNum+1}"/>">&#62;</a></li>
    </c:if>
    </ul>
</main>
</body>
</html>