<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>

<template:page title="About" active="about">
    <jsp:attribute name="content">
<div class="row">
        <div class="jumbotron">
            <span class="text-left">
                <p>Farmatec – действующее предприятие, занимающееся реализацией стоматологического оборудования. </p>
<p>Farmatec позиционирует себя на рынке в качестве дистрибьютора, который предоставляет весь спектр товаров, связанных с поставкой, установкой и техническим обслуживанием медицинского оборудования.</p>
<p>Кроме того, Farmatec постоянно развивает отношения с крупнейшими зарубежными производителями, получает новые партнерские сертификаты и статусы.</p>
<h3>Перспективные планы компании направлены на:</h3>
                <ul>
                    <li><h4>расширение спектра предлагаемых товаров и услуг.</h4></li>
                    <li><h4>внедрение в Казахстане новейших информационных и технических решений в области медицины.</h4></li>
                    <li><h4>качественный сервис, соответствующий международным стандартам качества.</h4></li>
                </ul>
                </p>
            </span>
            <div class="text-right"><a class="btn btn-default" href="<c:url value="/shop/contacts"/>">Наши контакты</a></div>

        </div>
</div>
    </jsp:attribute>
</template:page>