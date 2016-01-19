<%@ page import="java.net.*" %>
<!DOCTYPE>
<html>
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="common/css/mystyle.css">
	<title>欢迎注册</title>
	<script language="JavaScript" src="common/js/register.js"></script>
</head>

<body>
<div class="container">
	<header>
		
	</header>
	<main>
		<p>>><a href="login.html" class="back-link">返回登录</a></p>
		<div class="register">
			<form method="post" action="Register">
				<div class="fields">
					<p class="row">
						<lable for="user-name">用户名：</lable>
						<input type="text" id="user-name" name="username" class="field-large" />
					</p>
					<p class="row">
						<lable for="user-real-name">真实姓名：</lable>
						<input type="text" id="user-real-name" name="userrealname" class="field-large" />
					</p>
					<p class="row">
						<lable for="user-pwd1">设置密码：</lable>
						<input type="password" id="user-pwd1" name="userpwd1" class="field-large" />
					</p>
					<p class="row">
						<lable for="user-pwd2">确认密码：</lable>
						<input type="password" id="user-pwd2" name="userpwd2" class="field-large" />
					</p>
				</div>
				<%
				String erro = (String)request.getAttribute("erroinfo");
				if (erro != null && !erro.equals(""))
					out.print("<p class=\"service\"><a>*</a>"+ URLDecoder.decode(erro,"utf-8") +"</p>");
				%>
				<input  id="reg-submit" type="button" value="确认注册" class="btn" />
			</form>
		</div>
	</main>
</div>
</body>
</html>