<%-- 
    Document   : reply
    Created on : Apr 5, 2017, 2:40:42 AM
    Author     : Home
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hello Spring Forum</title>
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
        <h2>Reply</h2>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="replyForm">
            <form:label path="replierName">Name</form:label><br/>
            <form:input type="text" path="replierName" /><br/><br/>

            <form:label path="body">Body</form:label><br/>
            <form:textarea path="body" row="5" cols="30"/><br/><br/>
            <b>Attachments</b>
            <input type="file" name="attachments" multiple="multiple"/><br/><br/>
            <input type="submit" value="Submit"/>

        </form:form>
    </body>
</html>
