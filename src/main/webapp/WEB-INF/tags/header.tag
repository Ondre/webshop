<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="active" %>
<%@attribute name="user" type="com.epam.ap.entity.User" %>
<div class="container">
    <div id="header">
        <ul class="nav nav-pills pull-right">
            <li class="${active=="home"? "active" : ""}"><a href="<c:url value="/shop/home"/>">Домой</a></li>
            <li>
                <form class="navbar-form" role="search" action="<c:url value="/shop/search"/>" method="get">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Поиск" name="q">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </li>
            <li class="${active=="market" ? "active" : ""}"><a href="<c:url value="/shop/market"/>">Магазин</a>
            </li>
            <li class="${active=="about"? "active" : ""}"><a href="<c:url value="/shop/about"/>">О нас</a>
            </li>
            <li class="${active=="contacts"? "active" : ""}"><a href="<c:url value="/shop/contacts"/>">Контакты<span class="glyphicon glyphicon-send"></span></a>
            </li>

            <c:choose>
                <c:when test="${empty user}">
                    <li class="${active=="register"? "active" : ""}"><a href="<c:url value="/shop/register"/>">Регистрация</a>
                    </li>
                    <li class="${active=="login"? "active" : ""}"><a href="<c:url value="/shop/login"/>">Войти<span class="glyphicon glyphicon-log-in"></span></a></li>

                </c:when>
                <c:otherwise>
                    <li class="${active=="cart"? "active" : ""}"><a href="<c:url value="/shop/cart"/>"><span class="glyphicon glyphicon-user"></span>${fn:toUpperCase(user.login.charAt(0))}${fn:substring(user.login,1,user.login.length())} | В корзину<span class="glyphicon glyphicon-shopping-cart"></span></a>
                    </li>
                    <li><a href="<c:url value="/shop/logout"/>">Выход</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
        <h3>
            <a href="<c:url value="/shop/home"/>"><img src="<c:url value="../../img/logo.png"/>" width="300" height="40"></a>
        </h3>
    </div>
