window.onload = function() {
	
	var username_ob = document.getElementById("user-name");
	var psw_ob1 = document.getElementById("user-pwd1");
	var psw_ob2 = document.getElementById("user-pwd2");
	var reg_submit_ob = document.getElementById("reg-submit");
	
	username_ob.onblur = function() {
		var parent_node = username_ob.parentNode;
		removeDisplay(parent_node);
		var username = username_ob.value;
		
		var regx = new RegExp("^[0-9|A-Z|a-z|_]{1,64}$");
		var is_valid = regx.test(username);

		//判断用户名是否合法
		if (!is_valid) {
			//判断用户名是否为空
			if (username === "")
				display(parent_node,"请输入用户名");
			else
				display(parent_node,"须由字母数字下划线组成");
		}
		
		//
		//var reg = "\0*";
 		//if ()
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
		em_content.style.fontSize = "75%";
		em_content.appendChild(em_content_node);
		
		parent_node.appendChild(em_mark);
		parent_node.appendChild(em_content);
	}
	
}