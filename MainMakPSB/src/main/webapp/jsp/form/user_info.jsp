<%-- 
    Document   : index
    Created on : 06.07.2015, 16:33:43
    Author     : altmf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="header.jsp" />
        <title>Платформа отчетов</title>
    </head>   
    <body>
        <jsp:include page="logo.jsp" />        
        <jsp:include page="menu.jsp" />

        <div style=" margin:0 auto; width:80%;">
            <div class="container">
                <jsp:include page="notify.jsp" />
                <c:set var="mainContext" >${pageContext.request.contextPath}</c:set>
                <form action="${mainContext}/main/user_info.do" method="post" class="form">
                    <div>    
                        <fieldset class="left72">
                            <legend>Информация:</legend>
                            <c:if test="${isAdmin}">
                                <label for="current_login">Логин пользователя:</label>                            
                                <input type="text" name="current_login" id="current_login" value="${current_login}">
                                <button type="submit" name="submit_action" value="filter_login">Найти</button> <br/> 
                                <label for="roleList">Роли:</label>  
                                <select name="roleList" id="roleList">
                                    <c:forEach var="elem" items="${roleList}">
                                        <option value='${elem.id}'>${elem.name}</option>
                                    </c:forEach>
                                </select>
                                <button type="submit" name="submit_action" value="add_role">Добавить</button> <br/> 
                            </c:if>
                            <label for="user_role_list">Роли пользователя:</label> <br/> 
                            <display:table name="user_role_list" id="row" pagesize="50" requestURI="${mainContext}/main/user_info.do">
                                <display:column property="roleName" sortable="true" title="Роль" />
                                <display:column title="Действия">
                                    <a href="${pageContext.request.contextPath}/main/user_info.do?action=delete_role&amp;use_role_id=${row.userInfoId.useRoleId}">Удалить</a>
                                </display:column>
                            </display:table>
                            <p>В таблице "Роли пользователя" отображается информация о том, какие роли назначены пользователю, логин которого указан в поле "Пользователь".</p>
                            <label for="user_info_list">Детальная информация:</label> <br/>        
                            <display:table name="user_info_list" id="row" pagesize="50" requestURI="${mainContext}/main/user_info.do">
                                <display:column property="roleName" sortable="true" title="Роль" />
                                <display:column property="operation" sortable="true" title="Операция" />
                                <display:column property="resourcePath" sortable="true" title="Ресурс" />
                            </display:table>
                            <p>В таблице "Детальная информация" отображается информация о том, какие ресурсы доступны для указанной роли с указанной операцией 
                                (E - установка отчета в очередь выполнения, O - оперативное получение отчета)
                            </p>
                        </fieldset>       
                        <fieldset class="right20">
                            <legend>Пользователь</legend>
                            <p>${user_login}</p>
                            <br/>
                            <p>${user_name}</p>
                        </fieldset>
                    </div>
                </form>        
            </div>
        </div>
    </body>
</html>

