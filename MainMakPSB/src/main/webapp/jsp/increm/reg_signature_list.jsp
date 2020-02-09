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
            });

            function onFix(id) {

                $.post("${mainContext}/main/fix_signature.do", {id: id, action: "fix"})
                        .done(function (data) {
                            //if (data.status == 'ok'){
                            $("#fix_btn_" + id).prop('disabled', true);
                            //}
                        });
            }

            function overlay() {
                el = document.getElementById("overlay");
                el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
            }

            function openDetail(id) {
                el = document.getElementById("overlay");
                el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
                $.post("${mainContext}/main/fix_signature.do", {id: id, action: "info"})
                        .done(function (data) {
                            //alert(data.id);
                            $("#dfio").val(data.fio);
                            $("#dtimedetect").val(data.timeDetect);
                            $("#dsignatureName").val(data.signatureName);
                            $("#dsignatyreTypeName").val(data.signatyreTypeName);
                            $("#draion").val(data.raionName);
                            $("#dsnils").val(data.snils);
                            $("#ddate_born").val(data.dateBorn);
                            $("#dannotation").val(data.description);
                            $("#tannotation").css({"display" : "block"});
                            $("#tother").css({"display" : "none"});
                            //openTab(event, 'tannotation');
                            
                            var i = 0;
                            var s = '';
                            for (i = 0; i < data.errors.length; i++){
                                s = s + ('<p>' + data.errors[i].signatyreTypeName + ' - ' + data.errors[i].signatureName  + ' от ' + data.errors[i].timeDetect + '</p><br/>');
                            }
                            $("#tother").html(s);
                        });
            }
            
            function openTab(evt, tabName) {
            // Declare all variables
                var i, tabcontent, tablinks;

                // Get all elements with class="tabcontent" and hide them
                tabcontent = document.getElementsByClassName("tabcontent");
                for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }

                // Get all elements with class="tablinks" and remove the class "active"
                tablinks = document.getElementsByClassName("tablinks");
                for (i = 0; i < tablinks.length; i++) {
                    tablinks[i].className = tablinks[i].className.replace(" active", "");
                }

                // Show the current tab, and add an "active" class to the button that opened the tab
                document.getElementById(tabName).style.display = "block";
                evt.currentTarget.className += " active";
            }

        </script>
        <div style=" margin:0 auto; width:80%;">
            <div class="container">
                <jsp:include page="/jsp/form/notify.jsp" />
                <form action="${mainContext}/main/reg_signature_list.do" method="post" class="form">
                    <div>    
                        <a href="${pageContext.request.contextPath}/main/reg_signature_tree.do?">К иерархии</a>  
                        <fieldset >
                            <table style="width: 100%; margin-bottom: 0%;  ">
                             <tr>
                              <td style ="width: 22%; font-size: small;border-width: 2px ">Район</td>
                              <td style ="width: 18%; font-size: small;border-width: 2px ">СНИЛС (xxx-xxx-xxx xx)</td>
                              <td style ="font-size: small; border-width: 2px ">Вид ошибки</td>
                             </tr>
                            </table>
                            
                            <select class = "selectraion" name="raion" id="raion">
                                <option value = 0></option>
                                <c:forEach var="ra" items="${raions}">
                                    <option <c:if test="${ra.nomer eq raion}">selected</c:if> value='${ra.nomer}'>${ra.name}</option>
                                </c:forEach>
                            </select>

                            <input name = "snils" pattern="[0-9]{3}-[0-9]{3}-[0-9]{3} [0-9]{2}" class ="selectsnils" id="snils">
                            <select name="sel_sng_id" id="sel_sng_id" class="selecterror">
                                <option value = 0></option>                                
                                <c:forEach var="sgn" items="${listSignature}">
                                    <option <c:if test="${sgn.id eq sel_sng_id}">selected</c:if> value='${sgn.id}'>${sgn.name}</option>
                                </c:forEach>
                                
                            </select>
                            <br/>
                            <button type="submit" name="action" value="show">Выбрать</button><br/><br/>
                            
                            <display:table name="regSignList" id="row" pagesize="50" requestURI="/main/reg_signature_list.do">
                                <display:column property="idSignature.idSignatureType.name" sortable="true" title="Тип" />
                                <display:column property="idSignature.name" sortable="true" title="Ошибка" />
                                <display:column property="raion.name" sortable="true" title="Район" />
                                <display:column property="timeDetected" sortable="true" title="Дата выявления" format="{0,date,dd.MM.yyyy}" style="width: 30px"/>

                                <display:column property="snils" sortable="true" title="СНИЛС" style="width: 90px"/>
                                <display:column property="fio" sortable="true" title="ФИО" />
                                <display:column property="dateBorn" sortable="true" title="Дата рождения" format="{0,date,dd.MM.yyyy}" style="width: 30px"/>

                                <display:column title="Действия" style="width: 95px">
                                    <button type="button" value="${row.id}" style="font-size: 10px;" onclick="onFix(${row.id})" id="fix_btn_${row.id}" 
                                            <c:if test="${not empty row.timeFix}">disable = "true"</c:if>
                                                >ИСП</button>
                                            <button type="button" value="${row.id}" style="font-size: 10px;" onclick="openDetail(${row.id})" id="fix_btn_${row.id}">ДТЛ</button>
                                </display:column>
                            </display:table> 
                            <!--<input type="hidden" name="sgn_id" id="sgn_id" value="${sgn_id}">-->
                            <input type="hidden" name="type_id" id="type_id" value="${type_id}">
                        </fieldset>       
                    </div>
                </form>        
            </div>
        </div>
        <div id="overlay">
            <div class="overlaydiv">
                <label>Общая информация</label><br/>
                <label>ФИО: </label><input id="dfio" type="text" style="width: 90%"><br/>
                <label>СНИЛС: </label><input id="dsnils" type="text" style="width: 15%">
                <label>Дата рождения: </label><input id="ddate_born" type="text" style="width: 15%"><br/>
                <label>Район: </label><input id="draion" type="text" style="width: 40%"><br/>
                <label>Информация об ошибке</label><br/>
                <label>Время вывления: </label><input id="dtimedetect" type="text" style="width: 20%"><br/>
                <label>Тип ошибки: </label><input id="dsignatyreTypeName" type="text" style="width: 70%"><br/>
                <label>Ошибка: </label><input id="dsignatureName" type="text" style="width: 90%"><br/>
                 <div class="tab">
                    <button class="tablinks" onclick="openTab(event, 'tannotation')">Аннотация</button>
                    <button class="tablinks" onclick="openTab(event, 'tother')">Другие ошибки</button>
                  </div>

                  <div id="tannotation" class="tabcontent">
                        <textarea id="dannotation" style="width: 90%"></textarea><br/>
                  </div>

                  <div id="tother" class="tabcontent">                   
                  </div><br/>
                <button type="button" onclick="overlay()">Закрыть</button>
            </div>
        </div>
    </body>
</html>

