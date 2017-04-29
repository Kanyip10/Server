<%-- 
    Document   : listUser
    Created on : Apr 10, 2017, 9:14:08 PM
    Author     : KanYip
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <security:authorize access="!hasAnyRole('USER', 'ADMIN')">
        <a href="<c:url value="/login" />">Login</a><br/><br/>
    </security:authorize>

    <security:authorize access="hasAnyRole('USER', 'ADMIN')">
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </security:authorize>
    <body>
        <h2>List of Users </h2>
        <a href="<c:url value="/user/create" />">Add User</a><br/><br/>
        <c:choose>
            <c:when test="${fn:length(users) == 0}">
                <i>There are no User in the system</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${users}" var="entry">
                    User Id: ${entry.id}
                    <br/>                    
                    Name:<c:out value="${entry.userName}" />
                    <br/>
                    <c:forEach items="${entry.roles}" var="role">
                        Role:<c:out value="${role}"/>
                        <br/>
                    </c:forEach>
                    <a href = "<c:url value = "/user/listUser/editUser/${entry.userName}" />">Edit User</a><br/>
                    <a href = "<c:url value = "/user/listUser/delete/${entry.userName}" />">Delete User</a><br/>
                    <br/>
                    <br/>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </body>
    <br/>
    <a href="<c:url value="/index" />">Home</a>
    <br/>
</html>