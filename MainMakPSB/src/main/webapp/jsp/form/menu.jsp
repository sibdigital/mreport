<%-- 
    Document   : logo
    Created on : 16.09.2014, 8:48:23
    Author     : 003-0818
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="mainContext" >${pageContext.request.contextPath}</c:set>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:if test="${not empty user or true}">
    <script type="text/javascript">

        function nomethod() {
            alert('Режим находится в разработке!');
        }
        ;

    </script>
    <div style="background-color: #c3d9ff; margin:0 auto; width:80%;">
        <div id='mainmenu'>
            <ul class="nav">
                <li><a href='#'><b>Общие</b></a>
                    <ul>
                        <li><a href='${mainContext}/main/dynreport_list.do'><span>Динамические отчеты</span> </a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</c:if>
<hr>

