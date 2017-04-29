<%-- 
    Document   : view
    Created on : Apr 4, 2017, 8:02:36 PM
    Author     : Home
    Purpose    : View the detail of the ticket, create reply
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
        <br/>
    </security:authorize>
        
    <body>
        <h2>Topic #${topicId}:</h2>
        <i>Creator <c:out value="${topic.createrName}" /> - <c:out value="${topic.title}" /></h2>
        <br>
        <h2>Content:</h2>
        <c:out value = "${topic.body}" />
        <br/>
        <br/>
        
        <c:if test="${fn:length(topicAttachments) > 0}">
            <h2>Attachments:</h2>
            <ul>
                <c:forEach items="${topicAttachments}" var="attachment" varStatus="status">
                    <li>
                    <security:authorize access="hasAnyRole('USER', 'ADMIN')">
                        <a href="<c:url value="${topicId}/attachment/${attachment.name}" />">
                    </security:authorize>
                    <c:out value="${attachment.name}" /></a>
                    </li>
                </c:forEach>
                <br/>
                <br/>
            </ul>  
        </c:if>

            <br>
            <br>
            <h2>Replies:</h2>        
            <h3>Number of replies:<c:out value="${fn:length(replies)}" /></h3>
            <br>
            <br>
            <c:if test="${fn:length(replies) > 0}">
                Reply: <br/>
                <i>
                    <c:forEach items="${replies}" var="reply" varStatus="status">
                        Replier:
                        <c:out value="${reply.replierName}" /><br/>
                        Content:
                        <c:out value="${reply.body}" /><br/>
                        <c:if test="${fn:length(replyAttachments) > 0}">
                        <br/>
                            Attachments:
                            <c:forEach items="${replyAttachments}" var="attachment" varStatus="status">
                                <li>
                                <a href="<c:url value="${topicId}/attachment/${attachment.name}" />">
                                <c:out value="${attachment.name}" /></a>
                                </li>
                            </c:forEach>
                        </c:if>
                        <br/><br/>
                    </c:forEach>
                </i>
            </c:if>
            <br>
            <br>
        <security:authorize access="hasAnyRole('USER', 'ADMIN')">
            <h2>Leave your comment here:</h2>
            <form:form method="POST" enctype="multipart/form-data" modelAttribute="replyForm">
                <form:label path="body">Body</form:label><br/>
                <form:textarea path="body" row="5" cols="30"/><br/><br/>
                <b>Attachments</b>
                <input type="file" name="attachments" multiple="multiple"/><br/><br/>
                <input type="submit" value="Submit"/>
            </form:form>
        </security:authorize>
        <a href="<c:url value="/topic" />">Return to list topics</a>
    </body>
    <br/>
    <a href="<c:url value="/index" />">Home</a>
    <br/>
</html>
