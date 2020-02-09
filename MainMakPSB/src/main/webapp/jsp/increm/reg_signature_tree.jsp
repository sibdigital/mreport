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
        <jsp:include page="/jsp/form/header.jsp" />
        <title>Платформа отчетов</title>
    </head>
    <body>
        <c:set var="mainContext" >${pageContext.request.contextPath}</c:set>
        <jsp:include page="/jsp/form/logo.jsp" />
        <jsp:include page="/jsp/form/menu.jsp" />

        <script type="text/javascript">

            $(document).ready(function () {
                initDatePicker("begin_date", null);
                initDatePicker("end_date", null);
            }); //

            </script>
            <div style=" margin:0 auto; width:80%;">
                <div class="container">
                <jsp:include page="/jsp/form/notify.jsp" />
                <form action="${mainContext}/main/reg_signature_tree.do" method="post" class="form">
                    <div>    
                        <fieldset class="left72">
                            <legend>Ошибки по классификации</legend>
                            <ul>
                                <c:forEach var="elem" items="${listSignatureType}">
                                    <li  id='${elem.id}'>
                                        <b><a href='${mainContext}/main/reg_signature_list.do?action=show&amp;type_id=${elem.id}'>${elem.name} </a></b>
                                        <ul>
                                            <c:forEach var="sgn" items="${listSignature}">                                              
                                                <c:if test="${elem.id eq sgn.idSignatureType.id}">                                                    
                                                    <li  id='${sgn.id}'>
                                                        <a href='${mainContext}/main/reg_signature_list.do?action=show&amp;sgn_id=${sgn.id}&amp;type_id=${elem.id}'>${sgn.name} </a>
                                                    </li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </li>
                                </c:forEach>
                            </ul>
                        </fieldset>       
                        <fieldset class="right20">
                            <legend>Аннотация</legend>
                            <p>
                            </p>
                        </fieldset>
                    </div>
                </form>        
            </div>
        </div>
    </body>
</html>

