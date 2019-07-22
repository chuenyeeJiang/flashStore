//动态菜单
function menuOnclick(thisUi) {
	console.log(thisUi);
	var thisUiParent = $(thisUi).parent("ul");
	var liList = $(thisUiParent).children("li");
	$(liList).slideToggle();
}

// 用户全选（取消）
function Appuser_AllChecked() {
	var nl = document.getElementsByName("appuser_checkbox");
	$("#Appuser_CencelAll").attr("checked", false);
	for ( var i = 0; i < nl.length; i++) {
		nl[i].checked = true;
	}
}

function Appuser_AllCencel() {
	var nl = document.getElementsByName("appuser_checkbox");
	$("#Appuser_CheckAll").attr("checked", false);
	for ( var i = 0; i < nl.length; i++) {
		nl[i].checked = false;
	}
}

// 消息提示
function msg() {
	var msg = $
	{
		msg
	}
	;
	if (msg != null || msg != "") {
		alert(msg);
	}
}
