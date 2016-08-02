<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link href="<c:url value="/css/error.css"/>" rel="stylesheet">
    <meta charset="UTF-8" content="text/html">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<div class="background-forward"></div>
<div class="container">
    <div class="jumbotron">
        <h3>Кажется, что-то пошло не так. Попробуйте зайти позже.<br>
        Ошибка: ${pageContext.errorData.statusCode}</h3>
        <a class="btn btn-info" href="<c:url value="/shop/home"/>">Домой</a>
    </div>
</div>
</body>
</html>
