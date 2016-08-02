<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags" %>
<template:page title="Users" active="allusers">
    <jsp:attribute name="content">
        <div>
            <div class="row">


                <h2>User list:</h2>
                <table class="table-bordered table-hover">
                    <th class="text-center">ID</th>
                    <th class="text-center">Login</th>
                    <th class="text-center">Email</th>
                    <th class="text-center">Role</th>
<c:forEach items="${users}" var="user">
    <tr>
        <td class="text-center col-lg-1"><c:out value="${user.id}"/></td>
        <td class="text-center col-lg-4"><c:out value="${user.login}"/></td>
        <td class="text-center col-lg-4"><c:out value="${user.email}"/></td>
        <td class="text-center col-lg-2"><c:out value="${user.role}"/></td>
    </tr>
</c:forEach>
                </table>
                <div class="container">
                        <%--For displaying Previous link except for the 1st page --%>
                    <div class="col-md-4">
                <c:if test="${currentPage != 1}">
                     <td><a href="<c:url value="/shop/allusers?page=${currentPage - 1}"/>">Previous</a></td>
                </c:if>
                    </div>
                    <div class="col-md-offset-4">
                        <table>
                            <tr>
            <c:forEach begin="1" end="${pagesCount}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="<c:url value="/shop/allusers?page=${i}"/>">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
                            </tr>
                        </table>
                    </div>
                    <div class="col-md-offset-8">
                            <%--For displaying Next link --%>
                <c:if test="${currentPage lt pagesCount}">
                     <td><a href="<c:url value="/shop/allusers?page=${currentPage + 1}"/>">Next</a></td>
                 </c:if>
                    </div>
                </div>

            </div>
        </div>
    </jsp:attribute>
</template:page>
