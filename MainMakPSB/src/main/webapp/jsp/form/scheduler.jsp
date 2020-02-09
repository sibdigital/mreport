<%-- 
    Document   : header
    Created on : 02.09.2014, 16:04:43
    Author     : 003-0802
--%>

    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
   
    <c:set var="mainContext" >${pageContext.request.contextPath}</c:set>
    <style type="text/css">
        label {float:left; padding-right:10px;}
        .field {clear:both; text-align:right; line-height:25px;}
        .main {float:left; display: none}
    </style>
    <script type="text/javascript">
             
        $(document).ready(function() {
            initDatePicker("scheduled_date", null);
        });
        
        $(function(){
            $('legend').click(function(){
                $(this).parent().find('.main').slideToggle("slow");
            });
        });

    </script>
    <fieldset>
        <legend>Параметры времени выполнения:</legend>
        <div class="main">
            <div class="field">
                <label for="fam">Дата:</label>
                <input type="text" name="scheduled_date" id="scheduled_date"><br>
            </div><br/>
           
            <div class="field">
                <label for="im">Час(0-23)</label>
                <select name="hour" id="hour"></select>
                <!--<input type="text" name="hour" id="hour"> -->
            </div><br/>

            <div class="field">
                <label for="ot">Минута(0-59)</label>
                <select name="min" id="min"></select>
                <!--<input type="text" name="min" id="min" value='0'>-->
            </div><br/>
<%--            <div class="field">--%>
<%--                <label for="ot">Все районы (если есть выбор района)</label>--%>
<%--               <input type="checkbox" name="all_raion" id="all_raion"><br>--%>
<%--            </div><br>--%>
            <button type="submit" name="submit_action" value="queue">В очередь</button> <br/> 
            <br/> 
            <!-- <input type="text" name="cron" id="cron"><br> -->          
            <div class="main">
                <label>Информация о заполнении очереди:</label>
                <table>
                <c:forEach var="item" items="${triggersInfo}" >
                    <tr><td><c:out value="${item}" /></td></tr>
                </c:forEach>
                </table>
            </div>
        </div>
        
    </fieldset>
<%--    <fieldset>--%>
<%--        <legend>Параметры периодического выполнения:</legend>--%>
<%--        <div class="main">--%>
<%--            <jsp:include page="scheduler_period.jsp" />--%>
<%--        </div>--%>
<%--    </fieldset>--%>
    
    
