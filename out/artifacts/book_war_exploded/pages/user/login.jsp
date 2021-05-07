<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员登录页面</title>
	<%--		静态包含base标签、css样式、jQuery文件--%>
	<%@include file="/pages/common/head.jsp"%></head>


<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>
		
		<div class="login_banner">
			<div id="l_content">
				<span class="login_word">欢迎登录</span>
			</div>

			<div id="content">
				<div class="login_form" style="height: 370px">
					<div class="login_box">
						<div class="tit">
							<h1>登录尚硅谷会员</h1>
						</div>

<%--						错误信息回显--%>
						<div class="msg_cont">
							<span class="errorMsg">
<%--									<%=request.getAttribute("msg")==null?"请输入用户名和密码" : request.getAttribute("msg")%>--%>
<%--									使用EL表达式替换jsp表达式,更简洁--%>
								${empty sessionScope.msg ? "请输入用户名和密码" : sessionScope.msg}
							</span>
						</div>

						<div class="form">
							<form action="userServlet?action=login" method="post">
<%--								<input type="hidden" name="action" value="login">--%>
								<label>用户名称：</label>
								<input class="itxt" type="text" placeholder="请输入用户名"
									   autocomplete="off" tabindex="1" name="username"
								<%--value="<%=request.getAttribute("username")==null? "":request.getAttribute("username")%>"--%>
<%--										如果登录失败，将用户名密码信息回显，让用户知道自己哪里输错了--%>
									   value="${requestScope.username}"
								/>
								<br />
								<br />
								<label>用户密码：</label>
								<input class="itxt" type="password" placeholder="请输入密码"
									   autocomplete="off" tabindex="1" name="password" />
								<br />
								<br />
								<div>
									记住账号：<input type="radio" name="" id="" >
									<a href="" style="color: coral;text-align: end" >忘记密码</a>
								</div>


								<input type="submit" value="登录" id="sub_btn" />

								<div style="text-align: center">
									没有账户？<a href="pages/user/regist.jsp" style="color: coral">立即注册</a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<%--	静态包含网页底部版权信息--%>
		<%@include file="/pages/common/footer.jsp"%>
</body>
</html>