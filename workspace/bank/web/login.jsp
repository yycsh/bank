<%@ page import="java.net.*" %>
<!DOCTYPE>
<html>
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="common/css/mystyle.css">
	<title>欢迎登录</title>
</head>

<body>
<div class="container">
	<header>
		
	</header>
	<main>
		<div class="left-image">
			<img src="img/bg-02.png" width="420" height="300"/>
		</div>
		<div class="right-log">
			<form method="post" action="login">
				<%
				String info = (String)request.getAttribute("registedinfo");
				if (info != null && !info.equals(""))
					out.print("<p class=\"service\">"+ URLDecoder.decode(info,"utf-8") +"</p>");
				%>
				<div class="fields">
					
					<p class="row">
						<lable for="stu-no"><a class="login-lable">用户名:</a></lable>
						<input type="text" id="user-name" name="username" class="field-large" />
					</p>
					<p class="row">
						<lable for="user-pwd"><a class="login-lable">密码:</a></lable>
						<input type="password" id="user-pwd" name="userpwd" class="field-large" />
					</p>
					<%
					String erro = (String)request.getAttribute("erroinfo");
					if (erro != null && !erro.equals(""))
						out.print("<p class=\"service\"><a>*</a>"+ URLDecoder.decode(erro,"utf-8") +"</p>");
					%>
				</div>
				<input type="submit" value="登录" class="btn" />
			</form>
			<p class="log-href">还没有帐号？<a href="register.html">注册</a></p>
		</div>
	</main>
</div>
</body>
</html>