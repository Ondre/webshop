<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="product" type="com.epam.ap.entity.Product" %>
<div class="col-xs-4">
    <div class="panel panel-info">
    <div class="panel-heading">
    <h4>${product.title}</h4>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="col-lg-12">
                <div class="image-holder-container">
                    <a href="<c:url value="${not empty product.imgPath ? product.imgPath : '../img/no_image.png'}"/>" class="highslide" onclick="return hs.expand(this)">
                        <img src="<c:url value="${not empty product.imgPath ? product.imgPath : '../img/no_image.png'}"/>" alt="Highslide JS"
                             title="Click to enlarge"/></a>
                    <%--<img src="<c:url value="${not empty product.imgPath ? product.imgPath : '../img/no_image.png'}"/>">--%>
                </div>

                <h4>Описание:</h4>
                <p class="description">
                ${product.description}
                </p>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-6">
                <p class="price">
                Цена: ${product.cost.amount.longValue()} тг./шт.
                </p>
            </div>
            <div class="col-lg-6">
                <form action="<c:url value="/shop/cart/add"/>" method="POST">
                    <input type="submit" class="btn btn-success" value="В корзину"/>
                    <input type="hidden" name="product" value="${product.id}">
                </form>
            </div>

        </div>

    </div>
    </div>
</div>