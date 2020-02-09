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
        <title>ППлатформа отчетов</title>
    </head>
    <body>
        <c:set var="mainContext" >${pageContext.request.contextPath}</c:set>
        <jsp:include page="/jsp/form/logo.jsp" />
        <jsp:include page="/jsp/form/menu.jsp" />

        <script type="text/javascript">

            $(document).ready(function () {
                initDatePicker("begin_date", null);
                initDatePicker("end_date", null);
            });

        </script>
        <div style=" margin:0 auto; width:80%;">
            <div class="container">
                <jsp:include page="/jsp/form/notify.jsp" />
                <form action="${mainContext}/main/signature_element.do" method="post" class="form">
                    <input type="hidden" name="signatureElementId" id="signatureElementId" value="${signatureElement.id}">
                    <div>    
                        <fieldset class="left50">
                            <legend class="wordwraplegend">  ${signatureElement.signatureName}</legend> 
                            <label for="dayOfMonth">День месяца:</label><br/>
                            <input type="text" name="dayOfMonth" id="dayOfMonth" value="${signatureElement.dayOfMonth}"><br/>
                            <label for="dayOfWeek">День недели</label><br/>
                            <input type="text" name="dayOfWeek" id="dayOfWeek" value="${signatureElement.dayOfWeek}"><br/>
                            <label for="hour">Час:</label><br/>
                            <input type="text" name="hour" id="hour" value="${signatureElement.hour}" ><br/>
                            <label for="minute">Минута:</label><br/>
                            <input type="text" name="minute" id="minute" value="${signatureElement.minute}"><br/>
                            <label for="signatureTypeName">Тип правила:</label><br/>
                            <select name="signatureType" id="signatureType">
                                <c:forEach var="elem" items="${listSignatureType}">
                                    <option <c:if test="${elem.id eq signatureElement.idSignatureType}">selected</c:if> value='${elem.id}'>${elem.name}</option>
                                </c:forEach>
                            </select><br>
                            <label for="periodType">Периодичность запуска:</label><br/>
                            <select name="periodType" id="periodType">
                                <c:forEach var="elem" items="${listPeriodType}">
                                    <option <c:if test="${elem.id eq signatureElement.idPeriodType}">selected</c:if> value='${elem.id}'>${elem.name}</option>
                                </c:forEach>
                            </select><br>
                            <label for="isActive">Активно:</label><br/>
                            <input type="checkbox" name="isActive" id="isActive" <c:if test="${signatureElement.isActive eq 1}">checked="TRUE"</c:if>><br/>
                                <button type="submit" name="submit_action" value="edit">Сохранить</button> 
                                <a href="${pageContext.request.contextPath}/main/signature_list.do?">К списку</a>  
                        </fieldset>      
                        <fieldset class="right40">
                            <legend>Аннотация</legend>
                            <p>${signatureElement.description}
                            </p>
                        </fieldset>
                        <div class="right40">
                            <label>Предыдущее время выполнения проверки: ${prevFireTime}</label><br/>
                            <label>Следующее время выполнения проверки: ${nextFireTime}</label><br/>
                            <label>Информация о периодичности запуска проверки:</label>
                            <table>
                                <c:forEach var="item" items="${triggersInfo}" >
                                    <tr><td><c:out value="${item}" /></td></tr>
                                </c:forEach>
                            </table>
                        </div>  

                    </div>
                </form>       
            </div>
        </div>
    </body>
</html>

