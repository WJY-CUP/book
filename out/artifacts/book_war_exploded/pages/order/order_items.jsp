<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车</title>
    <%--		静态包含base标签、css样式、jQuery文件--%>
    <%@include file="/pages/common/head.jsp" %>
</head>

<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
    <span class="wel_word">购物车</span>

    <%--			静态包含登陆成功之后的页面--%>
    <%@ include file="/pages/common/login_success_menu.jsp" %>

</div>

<div id="main">

<table>
    <tr>
        <td>商品名称</td>
        <td>数量</td>
        <td>单价</td>
        <td>金额</td>
    </tr>

        <c:forEach items="${requestScope.orderItems}" var="item">
            <tr>
                <td>${item.name}</td>
                <td>${item.count}</td>
                <td>${item.price}</td>
                <td>${item.totalPrice}</td>
            </tr>
        </c:forEach>

</table>

    <%--		如果没有商品则不需要显示清空购物车功能--%>
    <c:if test="${not empty sessionScope.cart.items}">
        <div class="cart_info">
            <span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.getTotalCount()}</span>件商品</span>
            <span class="cart_span">总金额<span class="b_price">${sessionScope.cart.getTotalPrice()}</span>元</span>
            <span class="cart_span"><a id="clearCart" href="cartServlet?action=clear">清空购物车</a></span>
            <span class="cart_span"><a href="orderServlet?action=createOrder" >去结账</a></span>
        </div>
    </c:if>


    </div>

    <%--	静态包含网页底部版权信息--%>
    <%@include file="/pages/common/footer.jsp" %>
    </body>
    </html>