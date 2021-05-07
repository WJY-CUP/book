<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>编辑图书</title>
	<%--		静态包含base标签、css样式、jQuery文件--%>
	<%@include file="/pages/common/head.jsp"%><style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
	
	h1 a {
		color:red;
	}
	
	input {
		text-align: center;
	}
</style>
</head>
<body>
		<div id="header">
			<img class="logo_img" alt="" src="../../static/img/logo.gif" >
			<span class="wel_word">编辑图书</span>

			<%--		静态包含菜单栏--%>
			<%@include file="/pages/common/manager_menu.jsp"%>


		</div>
		
		<div id="main">
			<form action="manager/bookServlet" method="post">
				<input type="hidden" name="pageNo" value="${param.pageNo}">
<%--				本页面既可以添加也可以修改，故隐藏域的action值需要动态变化--%>
				<input type="hidden" name="action" value="${empty param.id ?"add" : "update"}">
<%--				修改书籍信息需要知道要修改的数据id--%>
				<input type="hidden" name="id" value="${requestScope.book.id}">
				<table>
					<tr>
						<td>名称</td>
						<td>价格</td>
						<td>作者</td>
						<td>销量</td>
						<td>库存</td>
						<td colspan="2">操作</td>
					</tr>		
					<tr>
						<td><input name="name" type="text" value="${requestScope.book.name}"/></td>
						<td><input name="price" type="text" value="${requestScope.book.price}"/></td>
						<td><input name="author" type="text" value="${requestScope.book.author}"/></td>
						<td><input name="sales" type="text" value="${requestScope.book.sales}"/></td>
						<td><input name="stock" type="text" value="${requestScope.book.stock}"/></td>
						<td><input type="submit" value="提交"/></td>
					</tr>	
				</table>
			</form>
			
	
		</div>
		<%--	静态包含网页底部版权信息--%>
		<%@include file="/pages/common/footer.jsp"%>
</body>
</html>