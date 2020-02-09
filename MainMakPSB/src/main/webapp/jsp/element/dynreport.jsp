<%-- 
    Document   : viplataNasPunkt
    Created on : 26.01.2016, 16:18:06
    Author     : 003-0810 Astrahanceva
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/jsp/form/header.jsp" />
        <title>Платформа отчетов</title>
    </head>
    <body>
        <c:set var="mainContext" >${pageContext.request.contextPath}</c:set>
        <jsp:include page="/jsp/form/logo.jsp" />
        <jsp:include page="/jsp/form/menu.jsp" />

        <script type="text/javascript">
             
            $(document).ready(function() {
                initDatePicker("begin_date", null);
                initDatePicker("end_date", null);
            });
            
        </script>
        <div style=" margin:0 auto; width:80%;">
            <div class="container">
                <jsp:include page="/jsp/form/notify.jsp" />
                <form action="${mainContext}/main/dynreport_element.do" method="post" class="form">
                    <div>    
                        <fieldset class="left72">
                            <legend>${adn_name}</legend>                                                       
                            <label for="begin_date">Дата начала:</label><br>
                            <input type="text" name="begin_date" id="begin_date"><br>
                            <label for="end_date">Дата конца:</label><br>
                            <input type="text" name="end_date" id="end_date"><br>
<%--                            <label for="raion">Район:</label><br>--%>
<%--                            <select name="raion" id="raion">--%>
<%--                                <c:forEach var="ra" items="${raions}">--%>
<%--                                    <option <c:if test="${ra.nomer eq raion}">selected</c:if> value='${ra.nomer}'>${ra.name}</option>--%>
<%--                                </c:forEach>--%>
<%--                            </select><br>--%>
                            <button type="submit" name="submit_action" value="create">Сформировать отчет</button> 
                            <jsp:include page="/jsp/form/scheduler.jsp" />
                            <jsp:include page="/jsp/form/view_files.jsp" /> <br/>
                        </fieldset>       
                        <fieldset class="right20">
                            <legend>Аннотация</legend>
                            <p>
                            </p>
                        </fieldset>
                    </div>
                </form>   
                <jsp:include page="/jsp/form/download.jsp" />         
            </div>
        </div>
    </body>
</html>

