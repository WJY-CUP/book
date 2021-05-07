<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>尚硅谷会员注册页面</title>

<%--		<!--写base标签，永远固定相对路径跳转的结果-->--%>
<%--		<base href="http://localhost:8080/book/">--%>
<%--		<link type="text/css" rel="stylesheet" href="static/css/style.css" >--%>
<%--		<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>--%>

<%--		静态包含base标签、css样式、jQuery文件--%>
		<%@include file="/pages/common/head.jsp"%>


		<script type="text/javascript">
			// 页面加载完成之后
			$(function () {

				$("#username").blur(function () {
					//1 获取用户名
					var username = this.value;
					$.getJSON("http://localhost:8080/book/userServlet","action=ajaxExistsUsername&username=" + username,function (data) {
						if (data.existsUsername) {
							$("span.errorMsg").text("用户名已存在！");
						} else {
							$("span.errorMsg").text("用户名可用！");
						}
					});
				});


				// 给注册绑定单击事件
				$("#sub_btn").click(function () {
					// 验证用户名：必须由字母，数字下划线组成，并且长度为5到12位
					//1 获取用户名输入框里的内容
					var usernameText = $("#username").val();
					//2 创建正则表达式对象
					var usernamePatt = /^\w{5,12}$/;
					//3 使用test方法验证
					if (!usernamePatt.test(usernameText)) {
						//4 提示用户结果
						$("span.errorMsg").text("用户名不合法！");

						return false;
					}

					// 验证密码：必须由字母，数字下划线组成，并且长度为5到12位
					//1 获取用户名输入框里的内容
					var passwordText = $("#password").val();
					//2 创建正则表达式对象
					var passwordPatt = /^\w{5,12}$/;
					//3 使用test方法验证
					if (!passwordPatt.test(passwordText)) {
						//4 提示用户结果
						$("span.errorMsg").text("密码不合法！");
						return false;
					}

					// 验证确认密码：和密码相同
					//1 获取确认密码内容
					var repwdText = $("#repwd").val();
					//2 和密码相比较
					if (repwdText != passwordText) {
						//3 提示用户
						$("span.errorMsg").text("确认密码和密码不一致！");
						return false;
					}

					// 邮箱验证：xxxxx@xxx.com
					//1 获取邮箱里的内容
					var emailText = $("#email").val();
					//2 创建正则表达式对象
					var emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
					//3 使用test方法验证是否合法
					if (!emailPatt.test(emailText)) {
						//4 提示用户
						$("span.errorMsg").text("邮箱格式不合法！");

						return false;
					}

					// 验证码：现在只需要验证用户已输入。因为还没讲到服务器。验证码生成。
					var codeText = $("#code").val();

					//去掉验证码前后空格
					// alert("去空格前：["+codeText+"]")
					codeText = $.trim(codeText);
					// alert("去空格后：["+codeText+"]")

					if (codeText == null || codeText == "") {
						//4 提示用户
						$("span.errorMsg").text("验证码不能为空！");
						return false;
					}
					// 去掉错误信息
					$("span.errorMsg").text("");
				});

				// 给图片验证码绑定单击响应事件
				$("#code_img").click(function () {
					// 在事件响应的function函数中有一个this对象，就是正在响应事件的dom对象，也就是img标签
					// src为img标签的属性，可读可写
					this.src = "${basePath}kaptcha.jpg?time=" + new Date();

				});

			});

		</script>
		<style type="text/css">
		.login_form{
			height:420px;
			margin-top: 25px;
		}

	</style>
	</head>

	<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>

			<div class="login_banner">

				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>

				<div id="content">
					<div class="login_form" style="height: 450px">
						<div class="login_box">
							<div class="tit">
								<h1>注册尚硅谷会员</h1>
								<span class="errorMsg">
<%--									<%=request.getAttribute("msg")==null? "" : request.getAttribute("msg")%>--%>
<%--	EL 表达式在输出 null 值的时候，输出的是空串。jsp 表达式脚本输出 null 值的时候，输出的是 null 字符串。--%>
									${empty requestScope.msg ? "请输入用户名和密码" : requestScope.msg}
								</span>
							</div>
							<div class="form">

<%--								----------------------------输入表单----------------------------%>
								<form action="userServlet?action=regist" method="post">
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名"
<%--										   request.getAttribute的值如果是空，会输出null,但是用户看不懂这是啥--%>
<%--										   value="<%=request.getAttribute("username")==null? "" : request.getAttribute("username")%>"--%>
<%--										   requestScope.username的值如果为空，就会输出空串而不是null，所以省去了判断--%>
											value="${requestScope.username}"
										   autocomplete="off" tabindex="1" name="username" id="username" />
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码"
										   autocomplete="off" tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码"
										   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>电子邮件：</label>
									<input class="itxt" type="text" placeholder="请输入邮箱地址"
										   value="${requestScope.email}"
										   autocomplete="off" tabindex="1" name="email" id="email" />
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" type="text" name="code" style="width: 80px;" id="code" />
									<img id="code_img" alt="" src="kaptcha.jpg" style="float: right; margin-right: 40px; width: 110px; height: 30px">
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" />
									<div style="text-align: center">
										已有账户？<a href="pages/user/login.jsp" style="color: coral">立即登录</a>
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