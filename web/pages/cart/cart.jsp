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

<script type="text/javascript">

    $(function () {
        // 给删除的a标签绑定单击事件，用于删除的确认提示操作
        $("a.deleteClass").click(function () {
            // 在事件的function函数中，有一个this对象。这个this对象，是当前正在响应事件的dom对象。
            /**
             * confirm是确认提示框函数
             * 参数是它的提示内容
             * 它有两个按钮，一个确认，一个是取消。
             * 返回true表示点击了，确认，返回false表示点击取消。
             */
            // $( DOM 对象 ) 就可以转换成为 jQuery 对象
            return confirm("你确定要删除【" + $(this).parent().parent().find("td:first").text() + "】?");
            // return false// 阻止元素的默认行为===不提交请求
        });

        $("#clearCart").click(function () {
            return confirm("你确定要清空购物车吗?");
        });

        $(".updateCount").change(function () {

            // 获取商品名称
            var name = $(this).parent().parent().find("td:first").text();
            var id = $(this).attr('bookId');
            // 获取商品数量
            var count = this.value;
            if ( confirm("你确定要将【" + name + "】商品修改数量为：" + count + " 吗?") ) {
                //发起请求。给服务器保存修改
                location.href = "${requestScope.basePath}cartServlet?action=updateCount&count="+count+"&id="+id;
            } else {
                // defaultValue属性是表单项Dom对象的属性。它表示默认的value属性值。
                this.value = this.defaultValue;
            }

        });
    })

</script>

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
    <td>操作</td>
</tr>



    <c:if test="${not empty sessionScope.cart.items}">
        <c:forEach items="${sessionScope.cart.items}" var="item">
            <tr>
                <td>${item.value.name}</td>
                <td>
                    <input type="text" value="${item.value.count}" style="width: 50px"  class="updateCount"
                    bookId = ${item.value.id}>
                </td>
                <td>${item.value.price}</td>
                <td>${item.value.totalPrice}</td>
                <td><a href="cartServlet?action=delete&id=${item.value.id}" class="deleteClass">删除</a></td>
            </tr>
        </c:forEach>
    </c:if>
<%--    如果购物车为空，显示提示语--%>
    <c:if test="${empty sessionScope.cart.items}">
        <tr>
            <td colspan="5">
                <a href="index.jsp">当前购物车为空,返回主页添加商品~</a>
            </td>
        </tr>
    </c:if>

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