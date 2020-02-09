<%-- 
    Document   : loginerror
    Created on : 21.05.2015, 18:04:51
    Author     : 003-0802
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="form/header.jsp" />
        <title>Платформа отчетов</title>
    </head>  
    <body>
        <jsp:include page="form/logo.jsp" />        
        <jsp:include page="form/menu.jsp" />
        <div style=" margin:0 auto; width:80%; margin-top: 100px">
            <div class="container">
                <h1>Недостаточно прав</h1>
                <p>
                    Вы не обладаете ролью или набором привелегий для работы с ресурсом, к которому произведено обращение.
                    Для получения соотвествующей роли или набора привелегий обратитесь к администратору
                </p>
                <br>
                <a href="${pageContext.request.contextPath}/index.jsp">Вернуться на стартовую страницу...</a>
            </div>
        </div>
    </body>
</html>