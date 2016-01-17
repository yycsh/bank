window.onload = function() {
	document.getElementById("reg-submit").disabled = true;
	
	var username_ob = document.getElementById("user-name");
	username_ob.onblur = function() {
		var parent_node = username_ob.parentNode;
		var em_childs = parent_node.getElementsByTagName("em");
		var len = em_childs.length;
		for (var i=0; i<len; i++) {
			parent_node.removeChild(em_childs[0]);
		}
		var username = username_ob.value;
		//判断用户名是否为空
		if (username === "") {
			var em_mark_node = document.createTextNode("*");
			var em_mark = document.createElement("em");
			em_mark.style.color = "red";
			em_mark.appendChild(em_mark_node);
			
			var em_content_node = document.createTextNode("用户名不能为空");
			var em_content = document.createElement("em");
			em_content.style.fontSize = "75%";
			em_content.appendChild(em_content_node);
			
			parent_node.appendChild(em_mark);
			parent_node.appendChild(em_content);
		}
		//
		//var reg = "\0*";
 		//if ()
	}
	
	var psw_ob1 = document.getElementById("user-pwd1");
	var psw_ob2 = document.getElementById("user-pwd2");
	psw_ob2.disabled = true;
	
	psw_ob1.onblur = function() {
		var parent_node = psw_ob1.parentNode;
		var em_childs = parent_node.getElementsByTagName("em");
		var len = em_childs.length;
		for (var i=0; i<len; i++) {
			parent_node.removeChild(em_childs[0]);
		}
			
		var psw1 = psw_ob1.value;
		//
		var regx = new RegExp("^[0-9|A-Z|a-z]{6,}$");
		var is_valid = regx.test(psw1);
        //若密码不合法
		if (!is_valid) {
			var em_mark_node = document.createTextNode("*");
			var em_mark = document.createElement("em");
			em_mark.style.color = "red";
			em_mark.appendChild(em_mark_node);
			
			var em_content_node = document.createTextNode("须为至少6位的字母或数字");
			var em_content = document.createElement("em");
			em_content.style.fontSize = "72%";
			em_content.appendChild(em_content_node);
			
			parent_node.appendChild(em_mark);
			parent_node.appendChild(em_content);
		} else {
			psw_ob2.disabled = false;
		}
	}
	
	psw_ob2.onblur = function() {
		var parent_node = psw_ob2.parentNode;
		var em_childs = parent_node.getElementsByTagName("em");
		var len = em_childs.length;
		for (var i=0; i<len; i++) {
			parent_node.removeChild(em_childs[0]);
		}
			
		var psw2 = psw_ob2.value;
		//
		var regx = new RegExp("^[0-9|A-Z|a-z]{6,}$");
		var is_valid = regx.test(psw2);
        //判断密码是否合法
		if (!is_valid) {
			var em_mark_node = document.createTextNode("*");
			var em_mark = document.createElement("em");
			em_mark.style.color = "red";
			em_mark.appendChild(em_mark_node);
			
			var em_content_node = document.createTextNode("须为至少6位的字母或数字");
			var em_content = document.createElement("em");
			em_content.style.fontSize = "72%";
			em_content.appendChild(em_content_node);
			
			parent_node.appendChild(em_mark);
			parent_node.appendChild(em_content);
		}
		
	}
	
	function submitEnable() {

	}
}