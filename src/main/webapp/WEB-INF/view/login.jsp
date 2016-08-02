<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<template:page title="Login" active="login">
    <jsp:attribute name="content">
        <div class="row">
            <div class="col-lg-offset-3 col-lg-6">
        <div class="form-group">
            <form class="form-group form-group-lg" action="<c:url value="/shop/login" />" method="post">
                <h3>Вход:</h3>
                <input autofocus class="text-input form-control" type="text" name="login" placeholder="Логин"><br>
                <input class="form-control" type="password" name="password" placeholder="Пароль"><br>
                <input class="btn btn-success" type="submit" value="Войти"><br>
                <div class="error">${errorMessage}</div>
            </form>
        </div>
                </div>
        </div>
    </jsp:attribute>
</template:page>

