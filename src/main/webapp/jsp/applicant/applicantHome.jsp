<%@ include file="/jspf/directories.jspf"%>

<html lang="en">
    <head>
        <title>Home</title>
        <%@ include file="/jspf/headDirectives.jspf"%>
    </head>
<body>
    <%@ include file="/jspf/applicantLogInedHeader.jspf" %>

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
        <h3>Faculties</h3>
        <a class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
        Sort
        </a>
        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
        <li><a class="text-decoration-none" href="<c:url value="/applicant/home?sort=name&page=${pageNum}"/>">A-Z</a></li>
        <li><a class="text-decoration-none" href="<c:url value="/applicant/home?sort=nameDesc&page=${pageNum}"/>">Z-A</a></li>
        <li><a class="text-decoration-none" href="<c:url value="/applicant/home?sort=budgetPlaces&page=${pageNum}"/>">By budget places(from lower to bigger)</a></li>
        <li><a class="text-decoration-none" href="<c:url value="/applicant/home?sort=budgetPlacesDesc&page=${pageNum}"/>">By budget places(from bigger to lower)</a></li>
        <li><a class="text-decoration-none" href="<c:url value="/applicant/home?sort=totalPlaces&page=${pageNum}"/>">By total places(from lower to bigger)</a></li>
        <li><a class="text-decoration-none" href="<c:url value="/applicant/home?sort=totalPlacesDesc&page=${pageNum}"/>">By total places(from bigger to lower)</a></li>
        </ul>
        </a>
        <%--        list of faculties--%>
        <div>

        </div>
        <table class="table">
        <tr>
        <th>faculty</th>
        <th>budget places </th>
        <th>all places </th>
        </tr>
        <c:forEach items="${faculties}" var="faculty">
            <tr>
            <td>${faculty.name}</td>
            <td>${faculty.budgetPlaces}</td>
            <td>${faculty.totalPlaces}</td>
            <td>
            <c:choose>
                <c:when test = "${applied.contains(faculty)}">
                    <p><b>applied</b></p>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="/applicant/apply?facId=${faculty.id}"/>">
                    <button  class="btn btn-primary">apply</button>
                    </a>
                </c:otherwise>
            </c:choose>
            </td>
            </tr>
        </c:forEach>
        </table>
        <%--            pages--%>
        <ul class="pagination start-50 translate-middl">
        <c:if test="${pageNum!=1}">
            <li class="page-item"><a class="page-link" href="<c:url value="/applicant/home?sort=${sort}&page=${pageNum-1}"/>">< </a></li>
        </c:if>

        <c:forEach begin="1" end="${pageQty}" varStatus="loop">
            <c:if test="${loop.index!=pageNum}">
                <li class="page-item"><a class="page-link" href="<c:url value="/applicant/home?sort=${sort}&page=${loop.index}"/>">${loop.index}</a></li>
            </c:if>
            <c:if test="${loop.index==pageNum}">
                <li class="page-item"><div class="page-link">${loop.index}</div></li>
            </c:if>
        </c:forEach>

        <c:if test="${pageNum!=pageQty}">
            <li class="page-item"><a class="page-link" href="<c:url value="/applicant/home?sort=${sort}&page=${pageNum+1}"/>">&#62;</a></li>
        </c:if>
        </ul>
    </main>
</body>
</html>