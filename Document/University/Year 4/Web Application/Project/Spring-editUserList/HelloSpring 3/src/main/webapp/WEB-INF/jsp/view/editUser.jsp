<%-- 
    Document   : editUser
    Created on : 2017年4月13日, 上午10:23:08
    Author     : tasng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit</title> </head>
    <body>
        <h2>Update</h2>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="userForm">
            <%--
            <form:label path="username">Username</form:label>
                <br/> 
            <form:input type="text" path="username" />
            --%>
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
            <input type="submit" value="editUser"/>
        </form:form>
    </body>
    <br/>
    <a href="<c:url value="/index" />">Home</a>
    <br/>
</html>