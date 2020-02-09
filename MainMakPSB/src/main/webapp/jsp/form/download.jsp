<%-- 
    Document   : download
    Created on : 10.09.2015, 10:48:09
    Author     : altmf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="mainContext" >${pageContext.request.contextPath}</c:set>
<div class="left72">
    <!-- Element where elFinder will be created (REQUIRED) 
    <div id="elfinder"></div> -->
    <c:if test="${not empty downloadLink}">
        <c:set var="now" value="<%=new java.util.Date()%>"/>                    
        <b>
            <a href="${mainContext}${downloadLink}">
                Скачать отчет (Сформирован <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${now}"/>)
            </a>
        </b>
    </c:if>
</div>