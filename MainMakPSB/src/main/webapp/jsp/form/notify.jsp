<%-- 
    Document   : header
    Created on : 02.09.2014, 16:04:43
    Author     : 003-0818
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="mainContext" >${pageContext.request.contextPath}</c:set>
<c:if test="${not empty infoMsg}">                 
    <p class="info">
        ${infoMsg}
    </p>
</c:if>
    <c:if test="${not empty errorMsg}">                 
    <p class="error">
        ${errorMsg}
    </p>
</c:if>
    <c:if test="${not empty successMsg}">                 
    <p class="success">
        ${successMsg}
    </p>
</c:if>
    
    
