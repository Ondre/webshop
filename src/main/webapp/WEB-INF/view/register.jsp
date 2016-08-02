<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>
<template:page title="Register" active="register">
    <jsp:attribute name="content">
        <div class="row">
<div class="form-group">
    <div class="col-lg-offset-3 col-lg-6">
        <h3>Регистрация:</h3>
        <form class="form-group form-group-lg" action="<c:url value="/shop/register"/>" method="POST">
        <input class="form-control" type="text" name="login" placeholder="Логин" value="${login}"> <br>
        <input class="form-control" type="text" name="email" placeholder="E-mail" value="${email}"> <br>
        <input class="form-control" type="password" name="password" placeholder="Пароль"> <br>
        <input class="form-control" type="password" name="repeat" placeholder="Повторите пароль"> <br>
        <input class="btn btn-success" type="submit" value="Зарегестрироваться">
    </form>
        <p class="text-danger">${error}</p>
    </div>
</div>
        </div>
    </jsp:attribute>
</template:page>
