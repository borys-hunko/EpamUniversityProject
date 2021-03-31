    <%@ include file="/jspf/directories.jspf"%>
        <html lang="en">
        <head>
        <title>Login V15</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        </head>
        <body>
            <c:if test="${errorMsg==null||errrorMsg.isEmpty()}">
                    <c:out value="oopsr something went wrong"/>
            </c:if>
            <c:if test="${errorMsg!=null&&!errrorMsg.isEmpty()}">
                    <c:out value="${errorMsg}"/>
            </c:if
        </body>
        </html>