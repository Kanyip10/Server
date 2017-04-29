<%-- 
    Document   : register
    Created on : Apr 10, 2017, 7:47:39 PM
    Author     : KanYip
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <title>Register</title> </head>
    <body>
    <h2>Register</h2>
        <%--
        <c:url var="logoutUrl" value="/logout"/> 
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
        </form>
        --%>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="userForm">
        <form:label path="username">Username</form:label>
        <br/> 
        <form:input type="text" path="username" />
        <br/>
        <br/> 
        <form:label path="password">Password</form:label>
        <br/> 
        <form:input type="text" path="password" />
        <br/>
        <br/>
        <form:label path="roles">Roles</form:label>
        <br/> 
        <form:checkbox path="roles" value="ROLE_USER" />ROLE_USER 
        <form:checkbox path="roles" value="ROLE_ADMIN" />ROLE_ADMIN 
        <br/>
        <br/>
        <input type="submit" value="Register"/>
        </form:form>
    </body>
    <br/>
    <a href="<c:url value="/index" />">Home</a>
    <br/>
</html>
