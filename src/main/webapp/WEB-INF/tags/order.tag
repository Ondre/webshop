<%@ tag pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags" %>
<%@ attribute name="order" required="true" type="com.epam.ap.entity.Order" %>
<%@attribute name="product" type="com.epam.ap.entity.Product" %>
<c:forEach items="${order.products.keySet()}" var="product">
    <tr>
        <td>${product.title}</td>
        <td class="text-center">${product.cost.amount} тг.</td>
        <td class="text-center">${order.products.get(product)}</td>
        <td class="text-center">${product.cost.amount * order.products.get(product)} тг.</td>
        <td>
            <div class="form-group">
            <form  action="<c:url value="/shop/cart/add"/>" method="post">
                <input type="hidden" name="product" value="${product.id}">
                <input class="btn btn-success btn-block" type="submit" value="+">
            </form>
            <form  action="<c:url value="/shop/cart/remove"/>" method="post">
                <input type="hidden" name="product" value="${product.id}">
                <input class="btn btn-danger btn-block" type="submit" value="-">
            </form>
            </div>
        </td>
    </tr>
</c:forEach>