window.onload = function() {
	
	var username_ob = document.getElementById("user-name");
	var psw_ob1 = document.getElementById("user-pwd1");
	var psw_ob2 = document.getElementById("user-pwd2");
	var reg_submit_ob = document.getElementById("reg-submit");
	displayUserNameEm(username_ob.parentNode,"");
	
	username_ob.onfocus = function() {
		var parent_node = username_ob.parentNode;
		removeDisplay(parent_node);
		displayUserNameEm(parent_node,"点击验证用户名");
	}
	username_ob.onblur = function() {
		var parent_node = username_ob.parentNode;
		//removeDisplay(parent_node);
		var username = username_ob.value;
		var regx = new RegExp("^[0-9|A-Z|a-z|_]{1,64}$");
		var regx_c = new RegExp("^[\u4e00-\u9fa5]{1,36}$");
		var is_valid_en = regx.test(username);
		var is_valid_cn = regx_c.test(username);
		var is_valid = false;
		if (is_valid_cn || is_valid_en)
			is_valid = true;
		//判断用户名是否合法
		if (!is_valid) {
			//判断用户名是否为空
			if (username === "") {
				removeDisplay(parent_node);
				displayUserNameEm(parent_node,"请输入用户名");
			} else {
				removeDisplay(parent_node);
				displayUserNameEm(parent_node,"字母数字下划线组成");
			}
			
		} else {
			//displayUserNameEm(parent_node,"点击验证用户名");
		}
	}
	
	psw_ob1.onfocus = function() {
		removeDisplay(psw_ob1.parentNode);
		removeDisplay(psw_ob2.parentNode);	
	}
	psw_ob1.onblur = function() {
		var parent_node = psw_ob1.parentNode;
		removeDisplay(parent_node);
			
		var psw1 = psw_ob1.value;
		//
		var regx = new RegExp("^[0-9|A-Z|a-z]{6,128}$");
		var is_valid = regx.test(psw1);

        //若密码不合法
		if (!is_valid) {
			if (psw1 === "")
				display(parent_node, "请设置密码");
			else
				display(parent_node, "须为至少6位的字母或数字");
			//
			removeDisplay(psw_ob2.parentNode);

		} else {
			if (psw_ob1.value !== psw_ob2.value && psw_ob2.value !== "") {
				removeDisplay(psw_ob2.parentNode);
				display(psw_ob2.parentNode, "两次输入密码不匹配");
			}
		}
	}
	
	psw_ob2.onfocus = function() {
		if (psw_ob1.value === "") {
			removeDisplay(psw_ob1.parentNode);
			display(psw_ob1.parentNode, "请设置密码");
		}
		
	}
	psw_ob2.onblur = function() {
		var parent_node = psw_ob2.parentNode;
		removeDisplay(parent_node);
		var psw2 = psw_ob2.value;
		//
		var regx = new RegExp("^[0-9|A-Z|a-z]{6,128}$");
		var is_same = false;
		if (psw_ob1.value === psw2)
			is_same = true;
        //判断密码是否匹配
		if (!is_same) {
			if (psw2 === "") display(parent_node, "请再次输入密码");
			else display(parent_node, "两次输入密码不匹配");
		}
	}
	
	reg_submit_ob.onclick = function() {
		var regx = new RegExp("^[0-9|A-Z|a-z|_]{1,}$");
		var username = username_ob.value;
		var is_valid_name = regx.test(username);
		
		var regx = new RegExp("^[0-9|A-Z|a-z]{6,}$");
		var psw1 = psw_ob1.value;
		var is_valid_psw = regx.test(psw1);
		
		var psw_confirmed = false;
		var psw2 = psw_ob2.value;
		if (psw1 === psw2)
			psw_confirmed = true;
		var form_ob = reg_submit_ob.parentNode;
		if (is_valid_name && is_valid_psw && psw_confirmed)
			form_ob.submit();
	}

}

function validateName() {
	var username_ob = document.getElementById("user-name");
	var i=1;
	var xmlhttp;
	var username = username_ob.value;
	var checked = false;
	var regx = new RegExp("^[0-9|A-Z|a-z|_]{1,64}$");
	var regx_c = new RegExp("^[\u4e00-\u9fa5]{1,36}$");
	var is_valid_en = regx.test(username);
	var is_valid_cn = regx_c.test(username);
	var is_valid = false;
	if (is_valid_cn || is_valid_en)
		is_valid = true;
	if (is_valid) {
		if (window.XMLHttpRequest)
		{// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp=new XMLHttpRequest();
		}
		else {// code for IE6, IE5
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState==4 && xmlhttp.status==200) {
				checked = true;
				var xmlDoc = xmlhttp.responseXML;
				
				//var is_registered = xmlDoc.getElementById("isreg");
				var is_registered = xmlDoc.getElementsByTagName("isregistered");
				
				var is_reged = is_registered[0].childNodes[0].nodeValue;
				if (is_reged == "true") {
					
					setTimeout("var username_ob = document.getElementById('user-name');removeDisplay( username_ob.parentNode);displayUserNameEm( username_ob.parentNode,'已被注册');",1000);
				} else {
					setTimeout("var username_ob = document.getElementById('user-name');removeDisplay( username_ob.parentNode);displayUserNameEm( username_ob.parentNode,'未注册，可以使用');",1000);
				}
			}
		}
		var url = "checkname?name="+username;
		xmlhttp.open("GET",url,true);
		xmlhttp.send();
		
		setTimeout("document.getElementById('check-btn').setAttribute('src', 'img/search-green-00.png');document.getElementById('check-btn').parentNode.nextSibling.innerHTML = '.'",i*200);
		i++;
		setTimeout("document.getElementById('check-btn').setAttribute('src', 'img/search-green-01.png');document.getElementById('check-btn').parentNode.nextSibling.innerHTML = '..'",i*200);
		i++;
		setTimeout("document.getElementById('check-btn').setAttribute('src', 'img/search-green-02.png');document.getElementById('check-btn').parentNode.nextSibling.innerHTML = '...'",i*200);
		i++;
		setTimeout("document.getElementById('check-btn').setAttribute('src', 'img/search-green-03.png');document.getElementById('check-btn').parentNode.nextSibling.innerHTML = '.'",i*200);
	}
}

function removeDisplay(parent_node) {
	var em_childs = parent_node.getElementsByTagName("em");
	var len = em_childs.length;
	for (var i=0; i<len; i++) {
		parent_node.removeChild(em_childs[0]);
	}
}
	
function display(parent_node, str) {
	var em_mark_node = document.createTextNode("*");
	var em_mark = document.createElement("em");
	em_mark.style.color = "red";
	em_mark.appendChild(em_mark_node);
	
	var em_content_node = document.createTextNode(str);
	var em_content = document.createElement("em");
	em_content.style.fontSize = "80%";
	em_content.appendChild(em_content_node);
	
	parent_node.appendChild(em_mark);
	parent_node.appendChild(em_content);
}
	
function displayUserNameEm(parent_node, str) {
	var em_check = document.createElement("em");
	var check_button = document.createElement("img")
	check_button.setAttribute("src", "img/search-green-00.png");
	check_button.setAttribute("id", "check-btn");
	check_button.setAttribute("onclick", "validateName()");
	em_check.style.display = "inline-block";
	em_check.style.position = "relative";
	em_check.style.top = "0.4em";
	em_check.appendChild(check_button);
	parent_node.appendChild(em_check);
	
	var em_text = document.createTextNode(str);
	var em_ob = document.createElement("em");
	em_ob.style.fontSize = "80%";
	em_ob.appendChild(em_text);
	parent_node.appendChild(em_ob);
}