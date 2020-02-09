<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    
    <!--jsp:include page="/form/header.jsp" /-->
    <head>
        <jsp:include page="form/header.jsp"/>
        <title>Платформа отчетов</title>
    </head>
    <body>
        <jsp:include page="form/logo.jsp"/>
        <c:set var="mainContext" >${pageContext.request.contextPath}</c:set>
        <div style=" margin:0 auto; width:80%; margin-top: 100px">
            <div class="container">
                <form action="${mainContext}/login.do" method="post" class="form">
                    <fieldset>
                        <legend>Вход</legend>
                        <br/>Имя пользователя:<br/><input type="text" class="text" name="login" value="${login}" />
                        <br/>Пароль:<br/><input type="password" class="text" name="password"/>
                        <br/>
                        <input type="submit" value="Вход">
                    </fieldset>
                </form>
            </div>
        </div>
    </body>
    
</html>