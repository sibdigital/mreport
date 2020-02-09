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
                <form action="${mainContext}/main/signature_list.do" method="post" class="form">
                    <div>    
                        <fieldset>
                            <display:table name="signList" id="row" pagesize="50" requestURI="/main/signature_list.do">
                                <display:column property="signatureTypeName" sortable="true" title="Тип правила" />
                                <display:column property="signatureName" sortable="true" title="Наименование" />
                                <display:column property="periodTypeName" sortable="true" title="Периодичность запуска" />
                                <display:column property="isActive" sortable="true" title="Активно" decorator="ru.p03.makpsb.ui.YesNoDecorator"/>
                                
                                <display:column property="dayOfMonth" sortable="true" title="День месяца" />
                                <display:column property="dayOfWeek" sortable="true" title="День недели" />
                                <display:column property="hour" sortable="true" title="Час" />
                                <display:column property="minute" sortable="true" title="Минута" />

                                <display:column title="Действия">
                                   <a href="${pageContext.request.contextPath}/main/signature_element.do?action=open&amp;id=${row.id}">Открыть</a>
                                </display:column>
                            </display:table> 
                        </fieldset>       
                    </div>
                </form>        
            </div>
        </div>
    </body>
</html>

