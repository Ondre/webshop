<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link href="<c:url value="/css/error.css"/>" rel="stylesheet">
    <meta charset="UTF-8" content="text/html">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<div class="background"></div>
<div class="background-forward"></div>
<div class="container">
    <div class="jumbotron">
        <h1>404</h1>
        <p>Такой страницы не существует ¯\_(ツ)_/¯</p>
        <a class="btn btn-info" href="<c:url value="/shop/home"/>">Домой</a>
    </div>
</div>
</body>
</html>
