<!DOCTYPE html>
<html>
    <head>
        <title>Hello Spring Forum</title>
    </head>
    <body>
        <c:url var="logoutUrl" value="/logout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
        <h2>Create a Topic</h2>
        <form:form method="POST" 
                   enctype="multipart/form-data" modelAttribute="topicForm">

            <form:label path="title">Title</form:label><br/>
            <form:input type="text" path="title" /><br/><br/>

            <form:label path="body">Body</form:label><br/>
            <form:textarea path="body" row="5" cols="30"/><br/><br/>
            <b>Attachments</b>
            <input type="file" name="attachments" multiple="multiple"/><br/><br/>
            <input type="submit" value="Submit"/>
        </form:form>
    </body>
    <br/>
    <a href="<c:url value="/index" />">Home</a>
    <br/>
</html>