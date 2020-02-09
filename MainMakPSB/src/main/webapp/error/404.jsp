<%-- 
    Document   : 404
    Created on : 01.12.2014, 13:51:14
    Author     : 003-0818
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="/form/header.jsp" />
    <body>
        <jsp:include page="/form/logo.jsp" />
        <div class="container">
            <h1>Страница не найдена!</h1>
            <br>
            Страницы по данному запросу не существует.
            <hr>
            <a href="${pageContext.request.contextPath}" ><i class="fa fa-angle-double-left"></i> На главную</a>
        </div>
    </body>

</html>
