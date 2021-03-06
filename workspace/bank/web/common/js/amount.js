﻿window.onload = function() {
	var transfer_account_ob = document.getElementById("tout-account")
	if (!transfer_account_ob && typeof(transfer_account_ob)!="undefined" && transfer_account_ob!=0)
	{} else {
		transfer_account_ob.onblur = function() {
			removeEm(transfer_account_ob.parentNode);
			if (transfer_account_ob.value == "")
				displayEm(transfer_account_ob.parentNode,"请输入用户名");
		}
	}
	var amount_ob = document.getElementById("out-amount");

	if (!amount_ob && typeof(amount_ob)!="undefined" && amount_ob!=0)
		amount_ob = document.getElementById("in-amount");

	if (!amount_ob && typeof(amount_ob)!="undefined" && amount_ob!=0)
		amount_ob = document.getElementById("tout-amount");
	var amount_parent_ob = amount_ob.parentNode;
	amount_ob.onblur = function() {
		var amount = amount_ob.value;
		removeEm(amount_parent_ob);
		var regx = new RegExp("^[0-9]{0,128}(.[0-9]{1,4})?$");
		var is_valid_amount = regx.test(amount);
		if (!is_valid_amount) {
			displayEm(amount_parent_ob,"请输入合法金额");
		}
	}
	
	function removeEm(parent_node) {
		var em_childs = parent_node.getElementsByTagName("em");
		var len = em_childs.length;
		alert(len);
		for (var i=0; i<len; i++) {
			parent_node.removeChild(em_childs[0]);
		}
	}
	function displayEm(parent_node, str) {
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