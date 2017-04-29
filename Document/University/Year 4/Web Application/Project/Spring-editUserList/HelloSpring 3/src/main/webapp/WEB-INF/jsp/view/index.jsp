<%-- 
    Document   : index
    Created on : Apr 5, 2017, 11:45:31 PM
    Author     : KanYip
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <h1>Welcome to Course Discussion Forum!</h1>
    <br>
    <h2>Category</h2>
</head>
<security:authorize access="!hasAnyRole('USER', 'ADMIN')">
    <a href="<c:url value="/login"/>">Login</a><br/><br/>
</security:authorize>

<security:authorize access="hasAnyRole('USER', 'ADMIN')">
    <c:url var="logoutUrl" value="/logout"/>
    <form action="${logoutUrl}" method="post">
        <input type="submit" value="Log out" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
</security:authorize>
<body>
    <ol>
        <li><a href="<c:url value="1/topic/list" />"><h3>Lecture</h3></a><br/></li>
        <li><a href="<c:url value="2/topic/list" />"><h3>Lab</h3></a><br/></li>
        <li><a href="<c:url value="3/topic/list" />"><h3>Others</h3></a><br/></li>
    </ol>
    <security:authorize access="hasRole('ADMIN')">
        <a href="<c:url value="/user/listUser" />"><h3>List User</h3></a><br/>
    </security:authorize>
    <a href="<c:url value="/user/create" />"><h3>Register</h3></a><br/>
</body>
</html>
