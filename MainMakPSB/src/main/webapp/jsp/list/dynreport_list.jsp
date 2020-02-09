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
             
            $(document).ready(function() {
                initDatePicker("begin_date", null);
                initDatePicker("end_date", null);
            });
            
        </script>
        <div style=" margin:0 auto; width:80%;">
            <div class="container">
                <jsp:include page="/jsp/form/notify.jsp" />
                <form action="${mainContext}/main/dynreport_list.do" method="post" class="form">
                    <div>    
                        <fieldset class="left72">
                            <display:table name="actualDynReportList" id="row" pagesize="50" requestURI="/main/dynreport_list.do">
                                <display:column property="viewName" sortable="true" title="Наименование отчета" />
                                <display:column property="version" sortable="true" title="Версия" />
                                <display:column property="code" sortable="true" title="Код" />
                                <display:column title="Действия">
                                   <a href="${pageContext.request.contextPath}/main/dynreport_element.do?action=open&amp;id=${row.id}">Открыть</a>
                                </display:column>
                            </display:table> 
                        </fieldset>       
                        <fieldset class="right20">
                            <legend>Аннотация</legend>
                            <p>Список динамических отчетов
                            </p>
                        </fieldset>
                    </div>
                </form>        
            </div>
        </div>
    </body>
</html>

