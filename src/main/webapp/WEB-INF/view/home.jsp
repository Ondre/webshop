<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>
<template:page title="Home" active="home">

    <jsp:attribute name="content">
<div class="row">
    <br>

    <h1 class="cover-heading text-center">Стоматологический магазин "Farmatec"</h1><br>
    <p class="lead">В нашем магазине вы можете приобрести расходные материалы, инструмент, оборудование для стоматологической практики.
        </p>

        <p class="lead">Широкий ассортимент продукции позволит  Вам закупить все необходимое для стоматологии в одном месте, при этом максимально сократив свои финансовые, транспортные и временные затраты.

        Мы предлагаем наиболее современную и популярную продукцию, применяемую в стоматологии.</p>
    <blockquote>
    <p>Если больной зуб удалять под громкую музыку, то врач совсем не почувствует боли.</p>
    <footer>М. Генин</footer>
</blockquote>
    <p class="lead">
        <c:choose>
        <c:when test="${empty user}">
    <p class="text-center"><a href="<c:url value="/shop/login"/>" class="btn btn-lg btn-default">Войти</a></p>
    </c:when>
                     <c:otherwise>
                         <p class="text-center"><a href="<c:url value="/shop/market"/>" class="btn btn-lg btn-default">В магазин</a></p>
                     </c:otherwise>
    </c:choose>
    </p>

</div>
    </jsp:attribute>
</template:page>