<%-- 
    Document   : header
    Created on : 02.09.2014, 16:04:43
    Author     : 003-0818
--%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
   
    <c:set var="mainContext" >${pageContext.request.contextPath}</c:set>
    
    <link rel="stylesheet" href="${mainContext}/css/screen.css" type="text/css" media="screen, projection">
    <link rel="stylesheet" href="${mainContext}/css/print.css" type="text/css" media="print"> 
    <link rel="stylesheet" href="${mainContext}/css/font.css" type="text/css"> 
    <link rel="stylesheet" href="${mainContext}/css/menustyles.css">
    <!--[if lt IE 9]>
        <link rel="stylesheet" href="${mainContext}/css/ie.css" type="text/css" media="screen, projection">
        <script type="text/javascript" src="${mainContext}/js/selectivizr-min.js"></script>
    <![endif]-->


    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="${mainContext}/scripts/formRegistration/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="${mainContext}/scripts/formRegistration/jquery.maskedinput.js"></script>
    <script type="text/javascript" src="${mainContext}/scripts/persnum/componentPersnum.js"></script>  
    <!--<script type="text/javascript" src="${mainContext}/scripts/menuscript.js"></script> -->
    
    <link rel="stylesheet" type="text/css" href="${mainContext}/resources/jquery-ui/jquery-ui-1.10.4.custom.css">
    <script type="text/javascript" src="${mainContext}/scripts/formRegistration/jquery-ui-1.10.4.custom.min.js"></script>
    <script type="text/javascript" src="${mainContext}/scripts/datePicker/localization.js"></script>
    <script type="text/javascript" src="${mainContext}/scripts/datePicker/componentDate.js"></script>
    <script type="text/javascript" src="${mainContext}/scripts/elFinder-2.1.11/js/elfinder.min.js"></script>
    
    <!-- 
    <link rel="stylesheet" type="text/css" href="${mainContext}/scripts/elFinder-2.1.11/js/jquery-ui.css">
    <script src="${mainContext}/scripts/elFinder-2.1.11/js/jquery-1.8.0.min.js"></script>
    <script src="${mainContext}/scripts/elFinder-2.1.11/js/jquery-ui.min.js"></script>

    <link rel="stylesheet" type="text/css" href="${mainContext}/scripts/elFinder-2.1.11/css/elfinder.min.css">
    <link rel="stylesheet" type="text/css" href="${mainContext}/scripts/elFinder-2.1.11/css/theme.css">

    <script src="${mainContext}/scripts/elFinder-2.1.11/js/elfinder.min.js"></script>

    <script src="${mainContext}/scripts/elFinder-2.1.11/js/i18n/elfinder.ru.js"></script>  -->

    <!-- elFinder initialization (REQUIRED) -->
    <!--  <script type="text/javascript" charset="utf-8">
            // Documentation for client options:
            // https://github.com/Studio-42/elFinder/wiki/Client-configuration-options
            $(document).ready(function() {
                    $('#elfinder').elfinder({
                            url : 'elfinder-servlet/connector',
                            , lang: 'ru'                    // language (OPTIONAL)
                    });
            });
    </script> -->
    
    
