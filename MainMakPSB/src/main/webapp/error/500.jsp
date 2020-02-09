<%-- 
    Document   : 500
    Created on : 01.12.2014, 13:51:31
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
            <h1>Произошла критическая ошибка!</h1>
            <br>
            Проверьте системный протокол для информации.
            <hr>
            <a href="${pageContext.request.contextPath}" ><i class="fa fa-angle-double-left"></i> На главную</a>
        </div>
    </body>

</html>
