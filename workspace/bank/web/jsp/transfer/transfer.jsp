<%@ page import="java.net.*" %>
<%@ page import="javax.servlet.http.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="common/css/mystyle.css">
	<title>转账</title>
	<script language="JavaScript" src="common/js/amount.js"></script>
</head>

<body>
<!--这个div装起了整个页面-->
<div class="container">
	<!-- ==导航== -->
	<header>
		
	</header>
	<!-- 结束导航 -->

	<!-- ==== 开始主体内容 ==== -->
	<main role="main">
		<!-- 主体内部左侧导航 -->
		<div class="main-left">
			<nav>
				<ul>
					<li><a href="to?d=withdraw">取款</a></li>
					<li><a href="to?d=save">存款</a></li>
					<li><a href="to?d=transfer">转账</a></li>
					<li><a href="tologs">历史记录</a></li>
					<li><a href="logout">退出</a></li>
				</ul>
			</nav>
		</div>
		<!-- 主体内部左侧导航结束 -->
		<!--  -->
		<div class="main-right">
			<%	
				Cookie[] cookies = request.getCookies();
				String name = "";
				for (Cookie cookie: cookies) {
					cookie.setPath("/");
					if (cookie.getName().equals("username")) {
						name = URLDecoder.decode(cookie.getValue(),"utf-8");
					}
				}
				out.print("<p class=\"service\">欢迎登录，"+ name +"</p>");
			%>
			<img src="img/bg-02.png" width="420" height="300"/>
			<div class="transfer">
				<form method="post" action="transfer">
					<div class="fields">
						<p>
							<lable for="out-account">转账账号:</lable>
							<input type="text" id="tout-account" name="taccount" class="field-large"/>
							<%
								if (request.getAttribute("username-erro") != null) {
									String erro = (String)request.getAttribute("username-erro");
									out.print("<a class=\"red\">*</a><a>"+ URLDecoder.decode(erro,"utf-8") +"</a>");
								}
							%>
						</p>
						<p>
							<lable for="out-amount">转账金额:</lable>
							<input type="text" id="tout-amount" name="tamount" class="field-large"/>
							<a>元</a>
							<%
								if (request.getAttribute("erro") != null) {
									String erro = (String)request.getAttribute("erro");
									out.print("<a class=\"red\">*</a><a>"+ URLDecoder.decode(erro,"utf-8") +"</a>");
								}
							%>
						</p>
					</div>
					<input type="submit" value="确认转账" class="btn" />
				</form>
			</div>
		</div>
	</main>
	<!-- 结束主体内容 -->
</div>
<!-- 页面容器到此为止 -->
</body>
</html>