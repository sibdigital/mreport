<%-- 
    Document   : header
    Created on : 13.02.2018, 16:04:43
    Author     : altmf
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
        function initSposob(visible, selval) {
            $('#field_dayOfMonth').css("visibility", "hidden");
            $('#field_dayOfWeek').css("visibility", "hidden");

            $(visible).css("visibility", "visible");
            $("#hsposob").val(selval);
        }
        $(document).ready(function () {
            initSposob('#field_dayOfWeek', "WEEKLY");
            fillStandartFormingPeriods();
        });
        
        function fillPeriods(_dayOfMonth, _dayOfWeek) {
            var base = document.URL.substring(0, document.URL.lastIndexOf('/') + 1);
            $.post(base + "available_periods.do", 
                {dayOfMonth: _dayOfMonth,
                 dayOfWeek: _dayOfWeek
                }
            )
            .done(function (data) {
                var hours = data.availableHours;
                var minutes = data.availableMinutes;

                var s = "";
                for (var i = 0; i < hours.length; i++) {
                    s += "<option value=\"" + hours[i] + "\">" + hours[i] + "</option>";
                }
                $("#hour_period").html(s);
                s = "";
                for (var i = 0; i < minutes.length; i++) {
                    s += "<option value=\"" + minutes[i] + "\">" + minutes[i] + "</option>";
                }
                $("#min_period").html(s);
            });
        }
        
        function fillStandartFormingPeriods(){
            var base = document.URL.substring(0, document.URL.lastIndexOf('/') + 1);
            $.post(base + "classifier.do",{
                classifier: 'ClsStandartFormingPeriod'
            }
            )
            .done(function (data) {
                var sfp = data.classifier_list;

                var s = "";
                for (var i = 0; i < sfp.length; i++) {
                    s += "<option value=\"" + sfp[i].id + "\">" + sfp[i].name + "</option>";
                }
                $("#periodType").html(s);
            });
        }

        function changeDayOfMonth() {
            fillPeriods($("#dayOfMonth").val(), -1);    
        }

        function changeDayOfWeek() {
            fillPeriods(-1, $("#dayOfWeek").val());
        }

        function initSposobAndChangePeriods(visible, selval) {
            $('#field_dayOfMonth').css("visibility", "hidden");
            $('#field_dayOfWeek').css("visibility", "hidden");

            $(visible).css("visibility", "visible");
            $("#hsposob").val(selval);

            fillPeriods(-1, -1);
        }
    </script>
    <!-- <fieldset>
         <legend>Параметры периодического выполнения:</legend>
         <div class="main"> -->

    <div>
        <label for="ot">Периодичность запуска</label><br/>
        <p><input name="sposob" type="radio" value="MONTHLY" onclick="initSposob('#field_dayOfMonth', 'MONTHLY')">
            Ежемесячно
        </p>
        <p><input name="sposob" type="radio" value="WEEKLY" onclick="initSposob('#field_dayOfWeek', 'WEEKLY')">
            Еженедельно
        </p>
        <p><input name="sposob" type="radio" value="DAILY" onclick="initSposobAndChangePeriods('#fake', 'DAILY')">
            Ежедневно
        </p>
    </div>
    <input type="hidden" name="hsposob" id="hsposob">
    <div class="field" id="field_dayOfMonth">
        <label for="im">День месяца</label>
        <select name="dayOfMonth" id="dayOfMonth" onchange="changeDayOfMonth()">
            <option selected value='1'>Первый день месяца</option>
            <option value='5'>Пятый день месяца</option>
            <option value='10'>Десятый день месяца</option>
            <option value='15'>Пятнадцатый день месяца</option>
            <option value='20'>Двадцатый день месяца</option>
        </select>
    </div><br/>
    <div class="field" id="field_dayOfWeek">
        <label for="im">День недели</label>
        <select name="dayOfWeek" id="dayOfWeek" onchange="changeDayOfWeek()">
            <option selected value='2'>Понедельник</option>
            <option value='3'>Вторник</option>
            <option value='4'>Среда</option>
            <option value='5'>Четверг</option>
            <option value='6'>Пятница</option>
            <option value='7'>Суббота</option>
            <option value='1'>Воскресенье</option>
        </select>
    </div><br/>
    <div class="field">
        <label for="im">Час(0-23)</label>
        <select name="hour_period" id="hour_period"></select>
    </div><br/>

    <div class="field">
        <label for="ot">Минута(0-59)</label>
        <select name="min_period" id="min_period"></select>
    </div><br/>
    <label for="periodType">Стандартный период формирования:</label><br/>
    <select name="periodType" id="periodType">
    </select><br/>
<%--<p>--%>
<%--    Периодическое формирование отчетов возможно только для отчетов <br/>--%>
<%--    не содержащих параметры и/или содержащих параметры: дата начала, дата конца, район; <br/>--%>
<%--    при этом формирование будет производиться в режиме "Все районы"--%>
<%--</p>--%>
<label for="isActive">Активно:</label><br/>
<input type="checkbox" name="isActive" id="isActive" <c:if test="${reportPeriod.isActive eq 1}">checked="TRUE"</c:if>><br/>
    <button type="submit" name="submit_action" value="save_sheduler_period">Сохранить</button><br/> 
    <div class="main">
        <label>Информация о заполнении очереди:</label>
        <table>
        <c:forEach var="item" items="${triggersInfo}" >
            <tr><td><c:out value="${item}" /></td></tr>
        </c:forEach>
    </table>
</div>
<!--</div>
</fieldset> -->


