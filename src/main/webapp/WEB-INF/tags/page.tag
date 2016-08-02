<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ attribute name="title" required="true" rtexprvalue="true" %>
<%@ attribute name="content" fragment="true" %>
<%@ attribute name="active"  %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link href="<c:url value="/css/justified-nav.css"/>" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/css/cover.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/highslide.css"/>">
    <meta charset="UTF-8" content="text/html">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<div class="site-wrapper">
    <div class="container">
    <template:header active="${active}" />
        <div class="clearfix"></div>

        <div class="container">

            <jsp:invoke fragment="content"/>

        </div>

    </div>
    <template:footer/>
</div>

<script src="<c:url value="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"/>"></script>
<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/js/highslide.js"/>"></script>

</body>
</html>