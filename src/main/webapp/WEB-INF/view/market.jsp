<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>
<template:page title="Market" active="market">
    <jsp:attribute name="content">
<div class="row">
    <h3>Страница ${currentPage} из ${pagesCount}.</h3>

    <div class="dropdown">
        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Сортировать по
            <span class="caret"></span></button>
        <ul class="dropdown-menu">
            <li class="${order eq 1 ? 'active' : ''}"><a
                    href="<c:url value="/shop/market?page=${currentPage}&orderBy=1"/>">цене по возрастанию</a></li>
            <li class="${order eq 2 ? 'active' : ''}"><a
                    href="<c:url value="/shop/market?page=${currentPage}&orderBy=2"/>">цене по убыванию</a></li>
            <li class="${order eq 3 ? 'active' : ''}"><a
                    href="<c:url value="/shop/market?page=${currentPage}&orderBy=3"/>">имени в алфав. порядке</a></li>
            <li class="${((order eq 0) or (empty order)) ? 'active' : ''}"><a
                    href="<c:url value="/shop/market?page=${currentPage}"/>">не сортировать</a></li>
        </ul>
    </div>

    <br>

    <div class="row">
        <div class="col-xs-1">
                <c:if test="${currentPage != 1}">
                     <td><a class="btn-sm btn-primary"
                            href="<c:url value="/shop/market?page=${currentPage - 1}${empty order ? '' : '&orderBy='}${empty order? '' : order}"/>"><-
                         Сюда</a></td>
                </c:if>
        </div>
        <div class="col-xs-10">

            <div class="btn-group">
            <c:forEach begin="1" end="${pagesCount}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                         <a class="btn btn-xs btn-primary active">${i}</a>
                    </c:when>
                    <c:otherwise>
                        <a type="button" class="btn btn-xs btn-primary"
                           href="<c:url value="/shop/market?page=${i}${empty order ? '' : '&orderBy='}${empty order? '' : order}"/>"> ${i} </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            </div>

        </div>
        <div class="col-xs-1">
                <%--For displaying Next link --%>
                <c:if test="${currentPage lt pagesCount}">
                     <td><a class="btn-sm btn-primary"
                            href="<c:url value="/shop/market?page=${currentPage + 1}${empty order ? '' : '&orderBy='}${empty order? '' : order}"/>">Туда
                         -></a></td>
                 </c:if>
        </div>
    </div>

    <br>
    <c:forEach items="${products}" varStatus="asd" var="product">
        <template:product product="${product}"/>
        <c:if test="${asd.count % 3 == 0}">
            <div class="clearfix"></div>
        </c:if>
    </c:forEach>
    <div class="clearfix"></div>

    <div class="row">
        <div class="col-xs-1">
                <c:if test="${currentPage != 1}">
                     <td><a class="btn-sm btn-primary"
                            href="<c:url value="/shop/market?page=${currentPage - 1}${empty order ? '' : '&orderBy='}${empty order? '' : order}"/>"><-
                         Сюда</a></td>
                </c:if>
        </div>
        <div class="col-xs-10">

            <div class="btn-group">
            <c:forEach begin="1" end="${pagesCount}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                         <a class="btn btn-xs btn-primary active">${i}</a>
                    </c:when>
                    <c:otherwise>
                        <a type="button" class="btn btn-xs btn-primary"
                           href="<c:url value="/shop/market?page=${i}${empty order ? '' : '&orderBy='}${empty order? '' : order}"/>"> ${i} </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            </div>

        </div>
        <div class="col-xs-1">
                <%--For displaying Next link --%>
                <c:if test="${currentPage lt pagesCount}">
                     <td><a class="btn-sm btn-primary"
                            href="<c:url value="/shop/market?page=${currentPage + 1}${empty order ? '' : '&orderBy='}${empty order? '' : order}"/>">Туда
                         -></a></td>
                 </c:if>
        </div>
    </div>
</div>
           </div>

    </jsp:attribute>
</template:page>