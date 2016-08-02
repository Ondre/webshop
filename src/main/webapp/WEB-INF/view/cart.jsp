<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>

<template:page title="Cart" active="cart">
    <jsp:attribute name="content">
        <div class="row">
            <br>
            <h2>Корзина:</h2>
            <br>

            <c:if test="${not empty order and order.products.size() gt 0}">


        <table class="table table-bordered col-lg-8">
            <th class="text-center">Название</th>
            <th class="text-center">Цена</th>
            <th class="text-center">Количество</th>
            <th class="text-center">Сумма</th>

            <template:order order="${order}"/>

            <tr>
                <td>Сумма заказа:</td>
                <td></td>
                <td></td>
                <td class="text-center">${sum.amount} тг.</td>
                <td><a href="#" class="btn btn-success btn-block">Заказ</a></td>
            </tr>


        </table>
</c:if>
            <c:if test="${empty order or order.products.size() lt 1}">
    <h1 class="cover-heading text-center">Увы, Ваша корзина еще пуста :( </h1><br>
    <p class="lead text-center">Но вы всегда можете ее пополнить.</p>

    <p class="lead">
    <p class="text-center"><a href="<c:url value="/shop/market"/>" class="btn btn-lg btn-default">В магазин</a></p>
    </p>
            </c:if>
        </div>
    </jsp:attribute>
</template:page>