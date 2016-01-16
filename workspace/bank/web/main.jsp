<%@ page import="java.net.*" %>
<%@ page import="javax.servlet.http.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="common/css/mystyle.css">
<title>主页</title>
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
				if (request.getAttribute("username") != null) {
					String name = (String)request.getAttribute("username");
					out.print("<p class=\"service\">欢迎登录，"+ name +"</p>");
				}
				
			%>
			<%
				if (request.getAttribute("username") == null) {
					Cookie[] cookies = request.getCookies();
					String name = "";
					for (Cookie cookie: cookies) {
						cookie.setPath("/");
						if (cookie.getName().equals("username")) {
							name = URLDecoder.decode(cookie.getValue(),"utf-8");
						}
					}
					out.print("<p class=\"service\">欢迎登录，"+ name +"</p>");
				}
				
			%>
			<img src="img/bg-02.png" width="420" height="300"/>
			<%	
				if (request.getAttribute("erro") != null) {
					String erro = (String)request.getAttribute("erro");
					out.print("<p class=\"service\"><a>*</a>"+ URLDecoder.decode(erro,"utf-8") +"</p>");
				}
				if (request.getAttribute("done") != null) {
					String info = (String)request.getAttribute("done");
					out.print("<p class=\"service\">"+ URLDecoder.decode(info,"utf-8") +"</p>");
				} else {
					out.print("<p class=\"service\">请选择服务项目...</p>");
				}
			%>
		</div>
	</main>
	<!-- 结束主体内容 -->
</div>
<!-- 页面容器到此为止 -->
</body>
</html>