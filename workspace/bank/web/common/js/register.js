window.onload = function() {
	document.getElementById("reg-submit").disabled = true;
	
	var username = document.getElementById("user-name");
	user-name.onblur = function() {
		
	}
	
	var psw_ob1 = document.getElementById("user-pwd1");
	psw_ob1.onblur = function() {
		var parent_node = psw_ob1.parentNode;
		var rm_childs = parent_node.getElementsByTagName("em");
		var len = rm_childs.length;
		for (var i=0; i<len; i++) {
			parent_node.removeChild(rm_childs[0]);
		}
			
		var psw1 = psw_ob1.value;
		if (psw1.length < 6) {
			var em_mark_node = document.createTextNode("*");
			var em_mark = document.createElement("em");
			em_mark.style.color = "red";
			em_mark.appendChild(em_mark_node);
			
			var em_content_node = document.createTextNode("密码大于等于6位");
			var em_content = document.createElement("em");
			em_content.style.fontSize = "75%";
			em_content.appendChild(em_content_node);
			
			psw_ob1.parentNode.appendChild(em_mark);
			psw_ob1.parentNode.appendChild(em_content);
		}
		submitEnable();
	}
	var psw_ob2 = document.getElementById("user-pwd2");
	psw_ob2.onblur = function() {
		
	}
	
	function submitEnable() {

	}
}