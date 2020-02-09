<%-- 
    Document   : loginerror
    Created on : 21.05.2015, 18:04:51
    Author     : 003-0807
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Платформа отчетов</title>
    </head>
    <body>
        <h1>Возникла ошибка</h1>
        <p>
            <b>Описание ошибки: </b>
            <%= request.getAttribute("msg_error") %>
        </p>
        <br>
        <a href="${pageContext.request.contextPath}/index.jsp">Вернуться на стартовую страницу...</a>
    </body>
</html>