    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">
        <head>
        <title>Login V15</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        </head>
        <body>
        <h1>Applicant</h1>
            <table>
                <tr>faculty </tr>
                <tr>budget places </tr>
                <tr>all places </tr>
                <c:forEach items="${faculties}" var="faculty">
                        <tr>
                            <td>${faculty.name}</td>
                            <td>${faculty.budgedPlaces}</td>
                            <td>${faculty.totalPlaces}</td>
                            <td>
                            <c:choose>
                                <c:when test = "${applied.contains(faculty)}">
                                    <p><b>applied</b></p>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value="/applicant/apply?facId=${faculty.id}"/>">
                                    <button>apply</button>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                            </td>
                        </tr>
                </c:forEach>
            </table>
        </body>
        </html>