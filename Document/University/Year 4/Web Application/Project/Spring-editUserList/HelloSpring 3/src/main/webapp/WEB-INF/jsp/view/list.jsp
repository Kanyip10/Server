<%-- 
    Document   : list
    Created on : Apr 4, 2017, 7:52:34 PM
    Author     : Home
    --------------Task of Michael--------------
    -----------------Test only-----------------
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hello Spring Forum</title>
    </head>
    <body>
        <h2>Topics</h2>
        <security:authorize access="!hasAnyRole('USER', 'ADMIN')">
            <a href="<c:url value="/login" />">Login</a><br/><br/>
        </security:authorize>

        <security:authorize access="hasAnyRole('USER', 'ADMIN')">
            <c:url var="logoutUrl" value="/logout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            </form>
            <br/>
            <a href="<c:url value="create" />">Create a Topic</a><br/><br/>
        </security:authorize>
        <c:choose>
            <c:when test="${fn:length(topicDatabase) == 0}">
                <i>There are no topics in the system</i>
            </c:when>
            <c:otherwise>
                <h3>Number of topics:<c:out value="${fn:length(topicDatabase)}" /></h3>
                <c:forEach items="${topicDatabase}" var="entry">
                    Topic ${entry.id}:
                    <a href="<c:url value="view/${entry.id}" />"><c:out value="${entry.title}" /></a>
                    (Created by: <c:out value="${entry.createrName}" />) <br/>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
    <br/>
    <a href="<c:url value="/index" />">Home</a>
    <br/>
</html>
