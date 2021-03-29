    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <html lang="en">
        <head>
        <title>Login V15</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style>
        .dropbtn {
        background-color: #4CAF50;
        color: white;
        padding: 16px;
        font-size: 16px;
        border: none;
        }

        .dropdown {
        position: relative;
        display: inline-block;
        }

        .dropdown-content {
        display: none;
        position: absolute;
        background-color: #f1f1f1;
        min-width: 160px;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
        z-index: 1;
        }

        .dropdown-content a {
        color: black;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
        }

        .dropdown-content a:hover {background-color: #ddd;}

        .dropdown:hover .dropdown-content {display: block;}

        .dropdown:hover .dropbtn {background-color: #3e8e41;}
        </style>
        </head>
        <body>
        <h1>Faculties</h1>
        <a href="<c:url value="/applicant/applications"/>"><button>My applications</button></a>
        <div class="dropdown">
        <button class="dropbtn">Sort</button>
        <div class="dropdown-content">
            <a href="<c:url value="/applicant/home?sort=name"/>">A-Z</a>
            <a href="<c:url value="/applicant/home?sort=nameDesc"/>">Z-A</a>
            <a href="<c:url value="/applicant/home?sort=budgetPlaces"/>">By budget places(from lower to bigger)</a>
            <a href="<c:url value="/applicant/home?sort=budgetPlacesDesc"/>">By budget places(from bigger to lower)</a>
            <a href="<c:url value="/applicant/home?sort=totalPlaces"/>">By total places(from lower to bigger)</a>
            <a href="<c:url value="/applicant/home?sort=totalPlacesDesc"/>">By total places(from bigger to lower)</a>
        </div>
        </div>
            <table>
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